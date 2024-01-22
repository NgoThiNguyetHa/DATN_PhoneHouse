var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/CuaHang')

const CuaHang = mongoose.model("cuaHang");
 

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

/* POST Mau. */

router.post('/addCuaHang', function(req, res, next) {
    const cuaHang = new CuaHang({
    diaChi: req.body.diaChi,
    username: req.body.username,
    password: req.body.password,
    email: req.body.email,
    sdt: req.body.sdt,
    phanQuyen: req.body.phanQuyen,
    trangThai: req.body.trangThai,
  })
  cuaHang.save()
  .then(data => {
    console.log(data)
    res.send(data)
  }).catch(err => {
    console.log
  })
});

/* GET loaidichvu listing. */
router.get('/getCuaHang', async (req,res) => {
  try {
    const cuaHang = await CuaHang.find();
    res.json(cuaHang);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
})

router.delete('/deleteCuaHang/:id', async (req,res) => {
  try{
    const data =  await CuaHang.findByIdAndDelete(req.params.id)
    if(!data){
      return res.status(404).json({message: "delete failed"})
    }else{
      return res.status(200).json({message: "delete successful"})
    }
  }catch(err){
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateCuaHang/:id", async (req, res ) => {
  try{
    const data = await CuaHang.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if(!data){
      return res.status(404).json({message: "update failed"})

    }else{
      return res.status(200).json({message: "update successful"})

    }
  }catch(err){
    return res.status(500).json({message: err.message})
  }
})

router.post('/dangNhapCuaHang', async (req, res) => {
    try {
        const { email, password } = req.body;
        if (!email || !password) {
            return res.status(400).json({ errorMessage: 'Vui lòng nhập email và mật khẩu.' });
        }

        const cuaHang = await CuaHang.findOne({ email });

        if (!cuaHang) {
            return res.status(401).json({ errorMessage: 'Email không tồn tại.' });
        }
        
    

        if (password !== cuaHang.password) {
            return res.status(401).json({ errorMessage: 'Mật khẩu không đúng.' });
        }

        return res.status(200).json({ successMessage: 'Đăng nhập thành công.', cuaHang });
    } catch (error) {
        console.error(error);
        res.status(500).json({ errorMessage: 'Lỗi server.' });
    }
});


module.exports = router;
