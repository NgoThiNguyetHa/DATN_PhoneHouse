var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/ChiTietGioHang')

const ChiTietGioHang = mongoose.model("chiTietGioHang")
 

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

/* POST Mau. */

router.post('/addChiTietGioHang', function(req, res, next) {
    const chiTietGioHang = new ChiTietGioHang({
    soLuong: req.body.soLuong,
    giaTien: req.body.giaTien,
    maDienThoai: req.body.maDienThoai,
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

/* GET loaidichvu listing. */
router.get('/getChiTietGioHang', async (req,res) => {
  try {
    const chiTietGioHang = await ChiTietGioHang.find();
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
