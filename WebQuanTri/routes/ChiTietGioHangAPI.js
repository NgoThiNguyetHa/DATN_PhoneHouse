var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/ChiTietGioHang')
require('../models/GioHang')


const ChiTietGioHang = mongoose.model("chiTietGioHang")
const GioHang = mongoose.model("gioHang")


/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

/* POST Mau. */

router.post('/addChiTietGioHang', function(req, res, next) {
    const chiTietGioHang = new ChiTietGioHang({
    soLuong: req.body.soLuong,
    giaTien: req.body.giaTien,
    maChiTietDienThoai: req.body.maChiTietDienThoai,
    maGioHang: req.body.maGioHang,
    
  })
  chiTietGioHang.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
});

/* GET chi tiêt giỏ hàng theo idGioHang. */
router.get('/getChiTietGioHang/:id', async (req,res) => {
  try {
    const chiTietGioHang = await ChiTietGioHang.find({maGioHang: req.params.id})
        .populate({
          path: "maChiTietDienThoai",
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
        })
        .populate({
          path: "maGioHang",
          populate: {path: "maKhachHang", model: "khachhang"}
        });
    res.json(chiTietGioHang);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

/* GET chi tiêt giỏ hàng theo idKhachHang. */
router.get('/getChiTietGioHangTheoKhachHang/:idKhachHang', async (req,res) => {
  try {
    const gioHangs = await GioHang.find({maKhachHang: req.params.idKhachHang});
    const maGioHang = gioHangs[0]._id;
    const chiTietGioHang = await ChiTietGioHang.find({maGioHang})
        .populate({
          path: "maChiTietDienThoai",
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
        })
        .populate({
          path: "maGioHang",
          populate: {path: "maKhachHang", model: "khachhang"}
        });
    res.json(chiTietGioHang);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteChiTietGioHang/:id', async (req,res) => {
  try{
    const data =  await ChiTietGioHang.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateChiTietGioHang/:id", async (req, res ) => {
  try{
    const data = await ChiTietGioHang.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if(!data){
      return res.status(404).json({message: "update failed"})

    }else{
      return res.status(200).json({message: "update successful"})

    }
  }catch(err){
    return res.status(500).json({message: err.message})
  }
})

module.exports = router;
