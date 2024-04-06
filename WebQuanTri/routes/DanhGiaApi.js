var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/DanhGia')
const {getStorage, ref, uploadBytesResumable, getDownloadURL} = require("firebase/storage");
const {firebaseApp} = require("../middleware/firebaseConfig");
const {v4: uuidv4} = require("uuid");
const {upload} = require("../middleware/multer");

const DanhGia = mongoose.model("danhgia");

async function uploadImage(file, quantity) {
  const storageFB = getStorage(firebaseApp);
  const randomString = uuidv4();
  if (quantity === 'single') {
    const dateTime = Date.now();
    const fileName = `${dateTime}${randomString}`;
    const storageRef = ref(storageFB, fileName);
    const metadata = { contentType: file.type };
    await uploadBytesResumable(storageRef, file.buffer, metadata);
    const downloadURL = await getDownloadURL(ref(storageFB, fileName));
    return downloadURL
  }
}
router.post('/addDanhGia', upload, async function(req, res, next) {
   try {
     const file = {
       type: req.file.mimetype,
       buffer: req.file.buffer
     }
     const buildImage = await uploadImage(file,'single');

     const danhGia = new DanhGia({
       noiDung: req.body.noiDung,
       hinhAnh: buildImage,
       diemDanhGia: req.body.diemDanhGia,
       ngayTao: req.body.ngayTao,
       idKhachHang: req.body.idKhachHang,
       idChiTietDienThoai: req.body.idChiTietDienThoai,
     })
     const saved = await danhGia.save();
     const data = await DanhGia.findById(saved._id)
         .populate("idKhachHang")
         .populate({
           path: "idChiTietDienThoai",
           populate: [
             {
               path: "maDienThoai",
               model:"dienthoai",
               populate: [
                 {path: 'maCuaHang', model: 'cuaHang'},
                 {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                 {path: 'maHangSX', model: 'hangSanXuat'}
               ]
             },
             {path: "maMau", model:"mau"},
             {path: "maDungLuong", model:"dungluong"},
             {path: "maRam", model:"ram"}
           ]
         });
     res.send(data)

   }catch (e) {
     console.log(e)
   }
});

/* GET loaidichvu listing. */
router.get('/getDanhGia', async (req,res) => {
  try {
    const danhGia = await DanhGia.find()
        .populate("idKhachHang")
        .populate({
          path: "idChiTietDienThoai",
          populate: [
            {
              path: "maDienThoai",
              model:"dienthoai",
              populate: [
                {path: 'maCuaHang', model: 'cuaHang'},
                {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                {path: 'maHangSX', model: 'hangSanXuat'}
              ]
            },
            {path: "maMau", model:"mau"},
            {path: "maDungLuong", model:"dungluong"},
            {path: "maRam", model:"ram"}
          ]
        });
    res.json(danhGia);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteDanhGia/:id', async (req,res) => {
  try{
    const data =  await DanhGia.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateDanhGia/:id", upload, async (req, res ) => {
  try{
    let hinhAnhURL = '';
    if (req.file) {
      const file = {
        type: req.file.mimetype,
        buffer: req.file.buffer
      }
      hinhAnhURL = await uploadImage(file,'single');
    }
    const update = {}
    if (req.body.noiDung) update.noiDung = req.body.noiDung
    if (req.body.diemDanhGia) update.diemDanhGia = req.body.diemDanhGia
    if (req.body.ngayTao) update.ngayTao = req.body.ngayTao
    if (req.body.idKhachHang) update.idKhachHang = req.body.idKhachHang
    if (req.body.idChiTietDienThoai) update.idChiTietDienThoai = req.body.idChiTietDienThoai
    if (hinhAnhURL) update.hinhAnh = req.body.hinhAnh

    const data = await DanhGia.findByIdAndUpdate(req.params.id, update, {new: true})
    if(!data){
      return res.status(404).json({message: "update failed"})

    }else{
      return res.status(200).json({message: "update successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})
  }
})

//get đánh giá theo chi tiet điện thoai
router.get('/getDanhGia/:id', async (req,res) => {
  try {
    const idChiTietDienThoai = req.params.id;
    const danhGia = await DanhGia.find({idChiTietDienThoai: idChiTietDienThoai})
        .populate("idKhachHang")
        .populate({
          path: "idChiTietDienThoai",
          populate: [
            {
              path: "maDienThoai",
              model:"dienthoai",
              populate: [
                {path: 'maCuaHang', model: 'cuaHang'},
                {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                {path: 'maHangSX', model: 'hangSanXuat'}
              ]
            },
            {path: "maMau", model:"mau"},
            {path: "maDungLuong", model:"dungluong"},
            {path: "maRam", model:"ram"}
          ]
        });
    res.json(danhGia);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})
module.exports = router;
