var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/HangSanXuat')

const HangSanXuat = mongoose.model("hangSanXuat")
 

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

/* POST Mau. */

router.post('/addHangSanXuat', function(req, res, next) {
    const hangSanXuat = new HangSanXuat({
    tenHang: req.body.tenHang,
    maCuaHang: req.body.maCuaHang,
  })
  hangSanXuat.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
});

/* GET loaidichvu listing. */
router.get('/getHangSanXuat', async (req,res) => {
  try {
    const hangSanXuat = await HangSanXuat.find();
    res.json(hangSanXuat);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.get('/getHangSanXuatTheoCuaHang/:maCuaHang', async (req,res) => {
  try {
    const maCuaHang = req.params.maCuaHang;
    const hangSanXuat = await HangSanXuat.find({ maCuaHang });
    res.json(hangSanXuat);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteHangSanXuat/:id', async (req,res) => {
  try{
    const data =  await HangSanXuat.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateHangSanXuat/:id", async (req, res ) => {
  try{
    const data = await HangSanXuat.findByIdAndUpdate(req.params.id, req.body, {new: true})
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
