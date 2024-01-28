var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/ChiTietDienThoai')

const ChiTietDienThoai = mongoose.model("chitietdienthoai");
 

router.post('/addChiTiet', function(req, res, next) {
    const chiTiet = new ChiTietDienThoai({
    dienThoai: req.body.dienThoai,
    kichThuoc: req.body.kichThuoc,
    manHinh: req.body.manHinh,
    camera: req.body.camera,
    pin: req.body.pin,
    heDieuHanh: req.body.heDieuHanh,
    cpu: req.body.cpu,
    doPhanGiai: req.body.doPhanGiai,
    namSanXuat: req.body.namSanXuat,
    thoiGianBaoHanh: req.body.thoiGianBaoHanh,
    moTaThem: req.body.moTaThem,
  })
  chiTiet.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
});

/* GET loaidichvu listing. */
router.get('/getChiTiet', async (req,res) => {
  try {
    const chiTiet = await ChiTietDienThoai.find();
    res.json(chiTiet);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteChiTiet/:id', async (req,res) => {
  try{
    const data =  await ChiTietDienThoai.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateChiTiet/:id", async (req, res ) => {
  try{
    const data = await ChiTietDienThoai.findByIdAndUpdate(req.params.id, req.body, {new: true})
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
