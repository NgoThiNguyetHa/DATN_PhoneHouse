var express = require('express');
var router = express.Router();
const mongoose = require('mongoose');
require('../models/ChiTietGioHang')
require('../models/GioHang')


const ChiTietGioHang = mongoose.model("chiTietGioHang")
const GioHang = mongoose.model("gioHang")


/* GET users listing. */
router.get('/', function (req, res, next) {
  res.send('respond with a resource');
});

/* POST Mau. */

router.post('/addChiTietGioHang/:idKhachHang', async function (req, res, next) {
  const idKhachHang = req.params.idKhachHang;

  try {
    // Tìm giỏ hàng của khách hàng dựa vào idKhachHang
    const gioHang = await GioHang.findOne({maKhachHang: idKhachHang});

    if (!gioHang) {
      return res.status(404).json('Giỏ hàng không tồn tại');
    }

    const existingChiTiet = await ChiTietGioHang.findOne({
      maChiTietDienThoai: req.body.maChiTietDienThoai,
      maGioHang: gioHang._id,
    }).populate([
      {
        path: "maChiTietDienThoai",
        populate: [
          {
            path: "maDienThoai",
            model: "dienthoai",
            populate: [
              {path: 'maCuaHang', model: 'cuaHang'},
              {path: 'maUuDai', model: 'uudai', populate: {path: 'maCuaHang', model: 'cuaHang'}},
              {path: 'maHangSX', model: 'hangSanXuat'}
            ]
          },
          {path: "maMau", model: "mau"},
          {path: "maDungLuong", model: "dungluong"},
          {path: "maRam", model: "ram"}
        ]
      },
      {
        path: "maGioHang",
        populate: {path: "maKhachHang", model: "khachhang"}
      }
    ]);

    if (existingChiTiet && ( parseFloat(existingChiTiet.soLuong)+parseFloat(req.body.soLuong))<= parseFloat(existingChiTiet.maChiTietDienThoai.soLuong)) {
      // Nếu đã tồn tại, chỉ cập nhật số lượng
      existingChiTiet.soLuong += req.body.soLuong;
      await existingChiTiet.save();
      // const populatedChiTiet = await ChiTietGioHang.findById(existingChiTiet._id)
      //     .populate([
      //       {
      //         path: "maChiTietDienThoai",
      //         populate: [
      //           {
      //             path: "maDienThoai",
      //             model: "dienthoai",
      //             populate: [
      //               {path: 'maCuaHang', model: 'cuaHang'},
      //               {path: 'maUuDai', model: 'uudai', populate: {path: 'maCuaHang', model: 'cuaHang'}},
      //               {path: 'maHangSX', model: 'hangSanXuat'}
      //             ]
      //           },
      //           {path: "maMau", model: "mau"},
      //           {path: "maDungLuong", model: "dungluong"},
      //           {path: "maRam", model: "ram"}
      //         ]
      //       },
      //       {
      //         path: "maGioHang",
      //         populate: {path: "maKhachHang", model: "khachhang"}
      //       }
      //     ]);
      // console.log("populatedChiTiet", populatedChiTiet);
      return res.json("Thêm thành công");
    }else if(existingChiTiet && ( parseFloat(existingChiTiet.soLuong)+parseFloat(req.body.soLuong)) > parseFloat(existingChiTiet.maChiTietDienThoai.soLuong)){
      return res.status(200).json(`Bạn đã có ${existingChiTiet.soLuong} sản phẩm trong giỏ hàng. Không thể thêm số lượng đã chọn vào giỏ hàng vì sẽ vượt quá giới hạn mua hàng của bạn.`);
    } else {
      // Nếu chưa tồn tại, tạo mới chi tiết giỏ hàng
      const chiTietGioHang = new ChiTietGioHang({
        soLuong: req.body.soLuong,
        giaTien: req.body.giaTien,
        maChiTietDienThoai: req.body.maChiTietDienThoai,
        maGioHang: gioHang._id,
      });
      // console.log("chiTietGioHang", chiTietGioHang);
      // Lưu chi tiết giỏ hàng

      const savedChiTietGioHang = await chiTietGioHang.save();
      const populateChiTietGioHang = await ChiTietGioHang
          .findById(savedChiTietGioHang._id)
          .populate({
            path: "maChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  {path: "maCuaHang", model: "cuaHang"},
                  {path: "maUuDai", model: "uudai", populate: "maCuaHang"},
                  {path: "maHangSX", model: "hangSanXuat"},
                ],
              },
              {path: "maMau", model: "mau"},
              {path: "maDungLuong", model: "dungluong"},
              {path: "maRam", model: "ram"},
            ],
          })
          .populate({
            path: "maGioHang",
            populate: {path: "maKhachHang", model: "khachhang"},
          });

      // Trả về kết quả
      // console.log("add chi tiet", chiTietGioHang);
      res.json("Thêm thành công");
    }
  } catch (err) {
    console.log(err);
    res.status(500).json('Đã xảy ra lỗi');
  }
});

/* GET chi tiêt giỏ hàng theo idGioHang. */
router.get('/getChiTietGioHang/:id', async (req, res) => {
  try {
    const chiTietGioHang = await ChiTietGioHang.find({maGioHang: req.params.id})
        .populate({
          path: "maChiTietDienThoai",
          populate: [
            {
              path: "maDienThoai",
              model: "dienthoai",
              populate: [
                {path: 'maCuaHang', model: 'cuaHang'},
                {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                {path: 'maHangSX', model: 'hangSanXuat'}
              ]
            },
            {path: "maMau", model: "mau"},
            {path: "maDungLuong", model: "dungluong"},
            {path: "maRam", model: "ram"}
          ]
        })
        .populate({
          path: "maGioHang",
          populate: {path: "maKhachHang", model: "khachhang"}
        });
    res.json(chiTietGioHang);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})

/* GET chi tiêt giỏ hàng theo idKhachHang. */
router.get('/getChiTietGioHangTheoKhachHang/:idKhachHang', async (req, res) => {
  try {
    const gioHangs = await GioHang.find({maKhachHang: req.params.idKhachHang});
    const maGioHang = gioHangs[0]._id;
    const chiTietGioHang = await ChiTietGioHang.find({maGioHang})
        .populate({
          path: "maChiTietDienThoai",
          populate: [
            {
              path: "maDienThoai",
              model: "dienthoai",
              populate: [
                {path: 'maCuaHang', model: 'cuaHang'},
                {path: 'maUuDai', model: 'uudai', populate: 'maCuaHang'},
                {path: 'maHangSX', model: 'hangSanXuat'}
              ]
            },
            {path: "maMau", model: "mau"},
            {path: "maDungLuong", model: "dungluong"},
            {path: "maRam", model: "ram"}
          ]
        })
        .populate({
          path: "maGioHang",
          populate: {path: "maKhachHang", model: "khachhang"}
        });
    res.json(chiTietGioHang);
  } catch (error) {
    res.status(500).json({error: error.message});
  }
})

router.delete('/deleteChiTietGioHang/:id', async (req, res) => {
  try {
    const data = await ChiTietGioHang.findByIdAndDelete(req.params.id)
    if (!data) {
      return res.status(404).json({message: "delete failed"})
    } else {
      return res.status(200).json({message: "delete successful"})
    }
  } catch (err) {
    return res.status(500).json({message: err.message})

  }
})


router.put("/updateChiTietGioHang/:id", async (req, res) => {
  try {
    const data = await ChiTietGioHang.findByIdAndUpdate(req.params.id, req.body, {new: true})
    if (!data) {
      return res.status(404).json({message: "update failed"})

    } else {
      return res.status(200).json({message: "update successful"})

    }
  } catch (err) {
    return res.status(500).json({message: err.message})
  }
})

router.put("/updateLoadListChiTietGioHang/:id", async (req, res) => {
  try {
    const idKhachHang = req.params.id;
    const chiTietGioHangList = req.body;
    const promises = chiTietGioHangList.map(async (chiTiet) => {
      const gioHang = await GioHang.findOne({maKhachHang: idKhachHang});
      if (!gioHang) {
        throw new Error("Không tìm thấy giỏ hàng cho khách hàng này");
      }
      const updatedChiTiet = await ChiTietGioHang.findOneAndUpdate(
          {
            maGioHang: gioHang._id,
            maChiTietDienThoai: chiTiet.maChiTietDienThoai,
          },
          {soLuong: chiTiet.soLuong},
          {new: true}
      );
      // console.log("chiTiet: ", updatedChiTiet);
      return updatedChiTiet;
    });

    const updatedChiTietGioHangList = await Promise.all(promises);

    if (updatedChiTietGioHangList.length > 0) {
      return res.status(200).json({message: "Cập nhật giỏ hàng thành công"});
    } else {
      return res
          .status(404)
          .json({message: "Không tìm thấy chi tiết giỏ hàng để cập nhật"});
    }
  } catch (err) {
    return res.status(500).json({message: err.message});
  }
});
module.exports = router;
