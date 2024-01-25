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
    idDienThoai: req.body.idDienThoai,
  })
  danhGia.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
});

/* GET loaidichvu listing. */
router.get('/getDanhGia', async (req,res) => {
  try {
    const danhGia = await DanhGia.find();
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


module.exports = router;
