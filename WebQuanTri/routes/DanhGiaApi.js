var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/DanhGia')

const DanhGia = mongoose.model("danhgia");
 
router.post('/addDanhGia', function(req, res, next) {
    const danhGia = new DanhGia({
    noiDung: req.body.noiDung,
    hinhAnh: req.body.hinhAnh,
    diemDanhGia: req.body.diemDanhGia,
    ngayTao: req.body.ngayTao,
    idKhachHang: req.body.idKhachHang,
    idChiTietDienThoai: req.body.idChiTietDienThoai,
  })
  danhGia.save()
  .then(data => {
    // console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
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


router.put("/updateDanhGia/:id", async (req, res ) => {
  try{
    const data = await DanhGia.findByIdAndUpdate(req.params.id, req.body, {new: true})
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
