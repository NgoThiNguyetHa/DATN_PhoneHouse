var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/ChiTietHoaDon')

const ChiTietHoaDon = mongoose.model("chiTietHoaDon")
 

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});


router.post('/addChiTietHoaDon', function(req, res, next) {
    const chiTietHoaDon = new ChiTietHoaDon({
    soLuong: req.body.soLuong,
    giaTien: req.body.giaTien,
    maHoaDon: req.body.maHoaDon,
    maChiTietDienThoai: req.body.maChiTietDienThoai,
  })
  chiTietHoaDon.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log(err)
  })
});

/* GET loaidichvu listing. */
router.get('/getChiTietHoaDon', async (req,res) => {
  try {
    const chiTietHoaDon = await ChiTietHoaDon.find().populate("maHoaDon").populate("maChiTietDienThoai");
    res.json(chiTietHoaDon);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteChiTietHoaDon/:id', async (req,res) => {
  try{
    const data =  await ChiTietHoaDon.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateChiTietHoaDon/:id", async (req, res ) => {
  try{
    const data = await ChiTietHoaDon.findByIdAndUpdate(req.params.id, req.body, {new: true})
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
