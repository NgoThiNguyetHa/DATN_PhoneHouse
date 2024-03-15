var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/UuDai')

const UuDai = mongoose.model("uudai")

/* GET DienThoai listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});
router.post('/addUuDai', function (req, res, next) {
  const uudai = new UuDai({
    giamGia: req.body.giamGia,
    thoiGian: req.body.thoiGian,
    trangThai: req.body.trangThai,
    maCuaHang: req.body.maCuaHang,
  })

  uudai.save()
      .then(data => {
        console.log(data)
        res.send(data)
      }).catch(err => {
    console.log(err)
  })
});
// lay uu dai theo cua hang
router.get('/getUuDai/:id', async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const uudai = await UuDai.find({maCuaHang: idCuaHang}).populate("maCuaHang");
    res.json(uudai);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})


router.delete('/deleteUuDai/:id', async (req, res) => {
  try {
    const data = await UuDai.findByIdAndDelete(req.params.id)
    if (!data) {
      return res.status(404).json({message: "delete failed"})
    } else {
      return res.status(200).json({message: "delete successful"})
    }
  } catch (err) {
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateUuDai/:id", async (req, res) => {
  try {
    const data = await UuDai.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if (!data) {
      return res.status(404).json({message: "update failed"})

    } else {
      return res.status(200).json({message: "update successful"})

    }
  } catch (err) {
    console.log(err);
    return res.status(500).json({message: err.message})
  }
})


module.exports = router;
