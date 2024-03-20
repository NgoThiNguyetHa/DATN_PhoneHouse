var express = require("express");
var router = express.Router();
const mongoose = require("mongoose");
require("../models/ChiTietDienThoai");
require("../models/DienThoai");

const ChiTietDienThoai = mongoose.model("chitietdienthoai");
const DienThoai = mongoose.model("dienthoai");

router.post("/addChiTiet", async function (req, res, next) {
  try {
    const chiTiet = new ChiTietDienThoai({
      soLuong: req.body.soLuong,
      giaTien: req.body.giaTien,
      maDienThoai: req.body.maDienThoai,
      maMau: req.body.maMau,
      maDungLuong: req.body.maDungLuong,
      maRam: req.body.maRam,
    });

    const savedDienThoaiChiTiet = await chiTiet.save(); // Lưu đối tượng
    const populatedDienThoaiChiTiet = await ChiTietDienThoai.findById(
      savedDienThoaiChiTiet._id
    )
      .populate({
        path: "maDienThoai",
        populate: [
          { path: "maCuaHang", model: "cuaHang" },
          { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
          { path: "maHangSX", model: "hangSanXuat" },
        ],
      })
      .populate("maMau")
      .populate("maDungLuong")
      .populate("maRam");

    // console.log(populatedDienThoaiChiTiet);
    res.send(populatedDienThoaiChiTiet);
  } catch (err) {
    console.log(err);
    res.status(500).send(err); // Trả về lỗi nếu có lỗi xảy ra
  }
});

/* GET loaidichvu listing. */
router.get("/getChiTiet", async (req, res) => {
  try {
    const chiTiet = await ChiTietDienThoai.find()
      .populate("maMau")
      .populate("maRam")
      .populate("maDungLuong")
      .populate({
        path: "maDienThoai",
        populate: [
          { path: "maHangSX maCuaHang" },
          { path: "maUuDai", populate: "maCuaHang" },
        ], // Liên kết với bảng 'hangSX'
      });
    res.json(chiTiet);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});
//get chi tiet theo dien thoai
router.get("/getChiTietTheoDienThoai/:maDienThoai", async (req, res) => {
  try {
    const maDienThoai = req.params.maDienThoai;
    const chitiet = await ChiTietDienThoai.find({ maDienThoai })
      .populate({
        path: "maDienThoai",
        populate: [
          { path: "maCuaHang", model: "cuaHang" },
          { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
          { path: "maHangSX", model: "hangSanXuat" },
        ],
      })
      .populate("maMau")
      .populate("maDungLuong")
      .populate("maRam");
    res.json(chitiet);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.delete("/deleteChiTiet/:id", async (req, res) => {
  try {
    const data = await ChiTietDienThoai.findByIdAndDelete(req.params.id);
    if (!data) {
      return res.status(404).json({ message: "delete failed" });
    } else {
      return res.status(200).json({ message: "delete successful" });
    }
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
});

router.put("/updateChiTiet/:id", async (req, res) => {
  try {
    const data = await ChiTietDienThoai.findByIdAndUpdate(
      req.params.id,
      req.body,
      { new: true }
    );
    if (!data) {
      return res.status(404).json({ message: "update failed" });
    } else {
      return res.status(200).json({ message: "update successful" });
    }
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
});

// getChiTietDienThoai Theo cửa hàng
router.get("/getChiTietDienThoaiTheoCuaHang/:id", async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const dienThoai = await DienThoai.find({ maCuaHang: idCuaHang })
      .populate("maCuaHang", "_id")
      .populate("maCuaHang");
    const chiTietDienThoais = [];
    for (const dt of dienThoai) {
      const dienThoai = await ChiTietDienThoai.find({ maDienThoai: dt._id })
        .populate("maRam")
        .populate("maDungLuong")
        .populate("maMau")
        .populate({
          path: "maDienThoai",
          populate: [
            { path: "maCuaHang", model: "cuaHang" },
            { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
            { path: "maHangSX", model: "hangSanXuat" },
          ],
        });
      if (dienThoai) {
        chiTietDienThoais.push(...dienThoai);
      }
    }
    res.json(chiTietDienThoais);
  } catch (error) {
    return res.status(500).json({ message: error.message });
  }
});

//get theo hãng sx
router.get("/getChiTietTheoHangSanXuat/:id", async (req, res) => {
  try {
    const idHangSX = req.params.id;
    const dienThoai = await DienThoai.find({ maHangSX: idHangSX })
      .populate("maHangSX", "_id")
      .populate("maHangSX");
    const chiTietDienThoais = [];
    for (const dt of dienThoai) {
      const dienThoai = await ChiTietDienThoai.find({ maDienThoai: dt._id })
        .populate("maRam")
        .populate("maDungLuong")
        .populate("maMau")
        .populate({
          path: "maDienThoai",
          populate: [
            { path: "maCuaHang", model: "cuaHang" },
            { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
            { path: "maHangSX", model: "hangSanXuat" },
          ],
        });
      if (dienThoai) {
        chiTietDienThoais.push(...dienThoai);
      }
    }
    res.json(chiTietDienThoais);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
