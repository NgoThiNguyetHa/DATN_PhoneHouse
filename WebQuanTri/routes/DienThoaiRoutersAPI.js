var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/DienThoai')

const DienThoai = mongoose.model("dienthoai")


/* GET DienThoai listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.post('/addDienThoai', function(req, res, next) {
    const dienthoai = new DienThoai({
    tenDienThoai: req.body.tenDienThoai,
    giaTien:req.body.giaTien,
    soLuong: req.body.soLuong,
    anh: req.body.anh,
    maMau: req.body.maMau,
    maRam: req.body.maRam,
    maDungLuong: req.body.maDungLuong,
  })
  dienthoai.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
});

router.get('/getDienThoai', async (req,res) => {
  try {
    const dienThoai = await DienThoai.find();
    res.json(dienThoai);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})


router.delete('/deleteDienThoai/:id', async (req,res) => {
  try{
    const data =  await DienThoai.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateDienThoai/:id", async (req, res ) => {
  try{
    const data = await DienThoai.findByIdAndUpdate(req.params.id, req.body, {new: true})
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
