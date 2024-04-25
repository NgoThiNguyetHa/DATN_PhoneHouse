var express = require("express");
var router = express.Router();
const mongoose = require("mongoose");
require("../models/ChiTietDienThoai");
require("../models/DienThoai");
require('../models/CuaHang')
const {
  getStorage,
  ref,
  uploadBytesResumable,
  getDownloadURL,
} = require("firebase/storage");
const { firebaseApp } = require("../middleware/firebaseConfig");
const { v4: uuidv4 } = require("uuid");
const { upload } = require("../middleware/multer");
const DanhGia = require("../models/DanhGia");
const HangSanXuat = require("../models/HangSanXuat");
const Mau = require("../models/Mau");
const Ram = require("../models/Ram");
const DungLuong = require("../models/DungLuong");
const ChiTietDienThoai = mongoose.model("chitietdienthoai");
const DienThoai = mongoose.model("dienthoai");
const CuaHang = mongoose.model("cuaHang");
router.post("/addChiTiet", async function (req, res, next) {
  try {
    const chiTiet = new ChiTietDienThoai({
      soLuong: req.body.soLuong,
      giaTien: req.body.giaTien,
      maDienThoai: req.body.maDienThoai,
      maMau: req.body.maMau,
      maDungLuong: req.body.maDungLuong,
      maRam: req.body.maRam,
      hinhAnh: req.body.hinhAnh,
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

    const transformedResponse = await Promise.all(
        chiTietDienThoais.map(async (item) => {
          const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
              .populate("idKhachHang")
              .populate({
                path: "idChiTietDienThoai",
                populate: [
                  {
                    path: "maDienThoai",
                    model: "dienthoai",
                    populate: [
                      { path: "maCuaHang", model: "cuaHang" },
                      { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                      { path: "maHangSX", model: "hangSanXuat" },
                    ],
                  },
                  { path: "maMau", model: "mau" },
                  { path: "maDungLuong", model: "dungluong" },
                  { path: "maRam", model: "ram" },
                ],
              });
          const averageRating = calculateAverageRating(danhGias);
          return {
            chiTietDienThoai: item,
            danhGias: danhGias,
            tbDiemDanhGia: averageRating
          };
        })
    );
    res.json(transformedResponse);
  } catch (error) {
    return res.status(500).json({ message: error.message });
  }
});

//get theo hãng sx
router.get("/getChiTietTheoHangSanXuat1/:id", async (req, res) => {
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

router.get("/getChiTietTheoHangSanXuat/:id", async (req, res) => {
  try {
    const idHangSX = req.params.id;
    const dienThoai = await DienThoai.find({ maHangSX: idHangSX })
      .populate("maHangSX", "_id")
      .populate("maHangSX");
    const danhGiaList = [];

    for (const dt of dienThoai) {
      const chiTietDienThoai = await ChiTietDienThoai.findOne({
        maDienThoai: dt._id,
      })
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

      if (chiTietDienThoai) {
        const danhGia = await DanhGia.find({
          idChiTietDienThoai: chiTietDienThoai._id,
        })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: ["maHangSX", "maUuDai", "maCuaHang"],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        danhGiaList.push({ chiTietDienThoai, danhGia });
      }
    }

    res.json(danhGiaList);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// router.get("/getChiTietTheoHangSanXuat/:id", async (req, res) => {
//   try {
//     const idHangSX = req.params.id;
//     const dienThoai = await DienThoai.find({ maHangSX: idHangSX })
//       .populate("maHangSX", "_id")
//       .populate("maHangSX");
//     const danhGiaList = [];

//     for (const dt of dienThoai) {
//       const chiTietDienThoai = await ChiTietDienThoai.findOne({
//         maDienThoai: dt._id,
//       })
//         .populate("maRam")
//         .populate("maDungLuong")
//         .populate("maMau")
//         .populate({
//           path: "maDienThoai",
//           populate: [
//             { path: "maCuaHang", model: "cuaHang" },
//             { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//             { path: "maHangSX", model: "hangSanXuat" },
//           ],
//         });

//       // Tìm đánh giá cho chi tiết điện thoại
//       const danhGia = await DanhGia.find({
//         idChiTietDienThoai: chiTietDienThoai ? chiTietDienThoai._id : null,
//       })
//         .populate("idKhachHang")
//         .populate({
//           path: "idChiTietDienThoai",
//           populate: [
//             {
//               path: "maDienThoai",
//               model: "dienthoai",
//               populate: ["maHangSX", "maUuDai", "maCuaHang"],
//             },
//             { path: "maMau", model: "mau" },
//             { path: "maDungLuong", model: "dungluong" },
//             { path: "maRam", model: "ram" },
//           ],
//         });

//       // Push chi tiết điện thoại và đánh giá tương ứng vào danh sách
//       danhGiaList.push({ chiTietDienThoai, danhGia });
//     }

//     res.json(danhGiaList);
//   } catch (error) {
//     res.status(500).json({ error: error.message });
//   }
// });

// router.get("/getChiTietTheoHangSanXuat/:id", async (req, res) => {
//   try {
//     const idHangSX = req.params.id;
//     const dienThoai = await DienThoai.find({ maHangSX: idHangSX })
//       .populate("maHangSX", "_id")
//       .populate("maHangSX");
//     const danhGiaList = [];

//     for (const dt of dienThoai) {
//       const chiTietDienThoai = await ChiTietDienThoai.findOne({
//         maDienThoai: dt._id,
//       })
//         .populate("maRam")
//         .populate("maDungLuong")
//         .populate("maMau")
//         .populate({
//           path: "maDienThoai",
//           populate: [
//             { path: "maCuaHang", model: "cuaHang" },
//             { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//             { path: "maHangSX", model: "hangSanXuat" },
//           ],
//         });

//       if (chiTietDienThoai) {
//         const danhGia = await DanhGia.find({ idChiTietDienThoai: chiTietDienThoai._id })
//           .populate("idKhachHang")
//           .populate({
//             path: "idChiTietDienThoai",
//             populate: [
//               { path: "maDienThoai", model: "dienthoai", populate: "maHangSX" },
//               { path: "maMau", model: "mau" },
//               { path: "maDungLuong", model: "dungluong" },
//               { path: "maRam", model: "ram" },
//             ],
//           });
//         danhGiaList.push({ chiTietDienThoai, danhGia });
//       }
//     }

//     res.json(danhGiaList);
//   } catch (error) {
//     res.status(500).json({ error: error.message });
//   }
// });

router.get("/getChiTietTheoHangSanXuatDG/:id", async (req, res) => {
  try {
    const idHangSX = req.params.id;
    // Tìm tất cả điện thoại của một hãng sản xuất
    const dienThoai = await DienThoai.find({ maHangSX: idHangSX });
    // Mảng để lưu kết quả cuối cùng
    const danhSachKetQua = [];

    // Duyệt qua từng điện thoại
    for (const dt of dienThoai) {
      // Tìm chi tiết điện thoại
      const chiTietDienThoais = await ChiTietDienThoai.find({
        maDienThoai: dt._id,
      })
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

      // Duyệt qua từng chi tiết điện thoại
      for (const chiTietDienThoai of chiTietDienThoais) {
        // Tìm danh sách đánh giá cho chi tiết điện thoại
        const danhGias = await DanhGia.find({
          idChiTietDienThoai: chiTietDienThoai._id,
        })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });

        // Thêm vào mảng kết quả
        danhSachKetQua.push({ chiTietDienThoai, danhGias });
      }
    }

    res.json(danhSachKetQua);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get("/getChiTietDienThoaiByID/:id", async (req, res) => {
  try {
    const data = await ChiTietDienThoai.find({ _id: req.params.id })
      .populate({
        path: "maDienThoai",
        populate: "maHangSX",
        populate: "maUuDai",
        populate: "maCuaHang",
      })
      .populate("maMau")
      .populate("maDungLuong")
      .populate("maRam");
    res.json(data);
  } catch (err) {
    return res.status(500).json({ message: err.message });
  }
});

async function uploadImage(file, quantity) {
  const storageFB = getStorage(firebaseApp);
  const randomString = uuidv4();
  if (quantity === "single") {
    const dateTime = Date.now();
    const fileName = `${dateTime}${randomString}`;
    const storageRef = ref(storageFB, fileName);
    const metadata = { contentType: file.type };
    await uploadBytesResumable(storageRef, file.buffer, metadata);
    const downloadURL = await getDownloadURL(ref(storageFB, fileName));
    return downloadURL;
  }
}
router.post("/addChiTietDienThoaiFirebase", upload, async (req, res, next) => {
  try {
    const file = {
      type: req.file.mimetype,
      buffer: req.file.buffer,
    };
    const buildImage = await uploadImage(file, "single");

    const chiTietDienThoai = new ChiTietDienThoai({
      soLuong: req.body.soLuong,
      giaTien: req.body.giaTien,
      maDienThoai: req.body.maDienThoai,
      maMau: req.body.maMau,
      maDungLuong: req.body.maDungLuong,
      maRam: req.body.maRam,
      hinhAnh: buildImage,
    });

    const saved = await chiTietDienThoai.save();
    res.status(201).json(saved);
  } catch (error) {
    console.error("Error:", error);
    res.status(500).send("Internal Server Error");
  }
});

router.put("/updateChiTietDienThoaiFirebase/:id", upload, async (req, res) => {
  try {
    const id = req.params.id;
    let hinhAnhURL = "";
    if (req.file) {
      const file = {
        type: req.file.mimetype,
        buffer: req.file.buffer,
      };
      hinhAnhURL = await uploadImage(file, "single");
    }

    const updated = {};
    if (req.body.maRam) updated.maRam = req.body.maRam;
    if (req.body.maDungLuong) updated.maDungLuong = req.body.maDungLuong;
    if (req.body.maMau) updated.maMau = req.body.maMau;
    if (req.body.maDienThoai) updated.maDienThoai = req.body.maDienThoai;
    if (req.body.giaTien) updated.giaTien = req.body.giaTien;
    if (req.body.soLuong) updated.soLuong = req.body.soLuong;
    if (hinhAnhURL) updated.hinhAnh = hinhAnhURL;

    const data = await ChiTietDienThoai.findByIdAndUpdate(id, updated, {
      new: true,
    });

    if (!data) {
      return res.status(404).json({ message: "update failed" });
    } else {
      return res.status(200).json({ message: "update successful", data });
    }
  } catch (err) {
    console.log(err);
    return res.status(500).json({ message: err.message });
  }
});

// router.get("/filterChiTietDienThoai", async (req, res) => {
//   try {
//     const { Gia } = req.query;
//     let giaTien = await ChiTietDienThoai.find();
//     if(giaTien){
//       giaTien = await ChiTietDienThoai.find({ giaTien: Number(Gia) });
//     }
//     res.json({giaTien});
//   } catch (error) {
//     console.log(error);
//     res.status(500).json({ error: error.message });
//   }
// });

// router.get("/filterGiaChiTietDienThoai", async (req, res) => {
//   try {
//     const { GiaMin, GiaMax } = req.query; // Thêm GiaMin và GiaMax từ query parameters
//     let giaTien;

//     // Kiểm tra xem cả hai giá trị đều tồn tại
//     if (GiaMin && GiaMax) {
//       giaTien = await ChiTietDienThoai.find({
//         giaTien: { $gte: Number(GiaMin), $lte: Number(GiaMax) },
//       })
//         .populate("maRam")
//         .populate("maDungLuong")
//         .populate("maMau")
//         .populate({
//           path: "maDienThoai",
//           populate: [
//             { path: "maCuaHang", model: "cuaHang" },
//             { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//             { path: "maHangSX", model: "hangSanXuat" },
//           ],
//         });
//     } else if (GiaMin) {
//       // Nếu chỉ có giá tối thiểu được cung cấp
//       giaTien = await ChiTietDienThoai.find({
//         giaTien: { $gte: Number(GiaMin) },
//       })
//         .populate("maRam")
//         .populate("maDungLuong")
//         .populate("maMau")
//         .populate({
//           path: "maDienThoai",
//           populate: [
//             { path: "maCuaHang", model: "cuaHang" },
//             { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//             { path: "maHangSX", model: "hangSanXuat" },
//           ],
//         });
//     } else if (GiaMax) {
//       // Nếu chỉ có giá tối đa được cung cấp
//       giaTien = await ChiTietDienThoai.find({
//         giaTien: { $lte: Number(GiaMax) },
//       })
//         .populate("maRam")
//         .populate("maDungLuong")
//         .populate("maMau")
//         .populate({
//           path: "maDienThoai",
//           populate: [
//             { path: "maCuaHang", model: "cuaHang" },
//             { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//             { path: "maHangSX", model: "hangSanXuat" },
//           ],
//         });
//     } else {
//       // Nếu không có thông tin về giá
//       giaTien = await ChiTietDienThoai.find()
//         .populate("maRam")
//         .populate("maDungLuong")
//         .populate("maMau")
//         .populate({
//           path: "maDienThoai",
//           populate: [
//             { path: "maCuaHang", model: "cuaHang" },
//             { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//             { path: "maHangSX", model: "hangSanXuat" },
//           ],
//         });
//     }

//     res.json(giaTien);
//   } catch (error) {
//     console.log(error);
//     res.status(500).json({ error: error.message });
//   }
// });

// router.get("/filterMoreChiTietDienThoai", async (req, res) => {
//   try {
//     const { Gia, RAM } = req.query;
//     let query = {};

//     // Kiểm tra nếu có yêu cầu lọc theo giá
//     if (Gia) {
//       query.giaTien = Number(Gia);
//     }

//     // Kiểm tra nếu có yêu cầu lọc theo RAM
//     if (RAM) {
//       // Tìm id của RAM từ schema Ram
//       const ramInfo = await Ram.findOne({ RAM: Number(RAM) });
//       if (ramInfo) {
//         query["maRam._id"] = ramInfo._id;
//       }
//     }

//     // Kiểm tra nếu có yêu cầu lọc theo dung lượng
//     // if (DungLuong) {
//     //   // Tìm id của DungLuong từ schema DungLuong
//     //   const dungLuongInfo = await DungLuong.findOne({
//     //     boNho: Number(DungLuong),
//     //   });
//     //   if (dungLuongInfo) {
//     //     query["maDungLuong._id"] = dungLuongInfo._id;
//     //   }
//     // }

//     let giaTien = await ChiTietDienThoai.find(query);

//     res.json({ giaTien });
//   } catch (error) {
//     console.log(error);
//     res.status(500).json({ error: error.message });
//   }
// });

// router.get("/filterBoNhoChiTietDienThoai", async (req, res) => {
//   try {
//     // Lấy dung lượng RAM từ query string
//     let boNho = req.query.boNho;

//     // Kiểm tra nếu dung lượng RAM được chỉ định trong query
//     if (!boNho) {
//       return res
//         .status(400)
//         .json({ message: "Vui lòng cung cấp dung lượng RAM." });
//     }

//     // Xử lý dungLuongRAM để tách thành mảng các giá trị
//     boNho = boNho.split(",").map((value) => parseInt(value.trim()));

//     // Tìm các điện thoại có dung lượng RAM phù hợp
//     const dienThoai = await ChiTietDienThoai.find({})
//       .populate("maRam")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       })
//       .populate({
//         path: "maDungLuong",
//         match: { boNho: { $in: boNho } },
//       })
//       .exec();

//     // Lọc ra các điện thoại có thông tin về dung lượng RAM phù hợp
//     const dienThoaiFiltered = dienThoai.filter((dt) => dt.maDungLuong !== null);

//     res.json(dienThoaiFiltered);
//   } catch (err) {
//     console.error(err);
//     res
//       .status(500)
//       .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
//   }
// });

// router.get("/filterRamChiTietDienThoai", async (req, res) => {
//   try {
//     // Lấy dung lượng RAM từ query string
//     let Ram = req.query.Ram;

//     // Kiểm tra nếu dung lượng RAM được chỉ định trong query
//     if (!Ram) {
//       return res
//         .status(400)
//         .json({ message: "Vui lòng cung cấp dung lượng RAM." });
//     }

//     // Xử lý dungLuongRAM để tách thành mảng các giá trị
//     Ram = Ram.split(",").map((value) => parseInt(value.trim()));

//     // Tìm các điện thoại có dung lượng RAM phù hợp
//     const dienThoai = await ChiTietDienThoai.find({})
//       .populate("maDungLuong")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       })
//       .populate({
//         path: "maRam",
//         match: { RAM: { $in: Ram } },
//       })
//       .exec();

//     // Lọc ra các điện thoại có thông tin về dung lượng RAM phù hợp
//     const dienThoaiFiltered = dienThoai.filter((dt) => dt.maRam !== null);

//     res.json(dienThoaiFiltered);
//   } catch (err) {
//     console.error(err);
//     res
//       .status(500)
//       .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
//   }
// });

// //sắp xếp theo giá cao - thấp
// router.get("/sapxepGiaCao-Thap", async (req, res) => {
//   try {
//     const data = await ChiTietDienThoai.find()
//       .sort({ giaTien: -1 })
//       .populate("maRam")
//       .populate("maDungLuong")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       });
//     res.json(data);
//   } catch (err) {
//     res.status(500).json({ message: err.message });
//   }
// });

// //sắp xếp theo giá thấp - cao
// router.get("/sapxepGiaThap-Cao", async (req, res) => {
//   try {
//     const data = await ChiTietDienThoai.find()
//       .sort({ giaTien: 1 })
//       .populate("maRam")
//       .populate("maDungLuong")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       }); ;
//     res.json(data);
//   } catch (err) {
//     res.status(500).json({ message: err.message });
//   }
// });

// //ưu đãi hot
// router.get("/uuDaiHot", async (req, res) => {
//   try {
//     const data = await ChiTietDienThoai.find()
//       .populate("maRam")
//       .populate("maDungLuong")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       });

//     // Lọc các bản ghi mà maUuDai là null hoặc trangThai của maUuDai là "Không Hoạt Động"
//     const filteredData = data.filter((item) => {
//       return (
//         item.maDienThoai.maUuDai &&
//         item.maDienThoai.maUuDai.trangThai === "Hoạt động"
//       );
//     });

//     // Ép kiểu trường giamGia từ string sang integer
//     filteredData.forEach((item) => {
//       if (item.maDienThoai.maUuDai && item.maDienThoai.maUuDai.giamGia) {
//         item.maDienThoai.maUuDai.giamGia = parseInt(
//           item.maDienThoai.maUuDai.giamGia
//         );
//       }
//     });

//     // Sắp xếp theo giamGia từ cao đến thấp
//     filteredData.sort((a, b) => {
//       const giamGiaA = a.maDienThoai.maUuDai
//         ? a.maDienThoai.maUuDai.giamGia
//         : 0;
//       const giamGiaB = b.maDienThoai.maUuDai
//         ? b.maDienThoai.maUuDai.giamGia
//         : 0;
//       return giamGiaB - giamGiaA;
//     });

//     res.json(filteredData);
//   } catch (err) {
//     res.status(500).json({ message: err.message });
//   }
// });

/// test

router.get("/filterGiaChiTietDienThoai", async (req, res) => {
  try {
    const { GiaMin, GiaMax } = req.query;
    let giaTien;

    if (GiaMin && GiaMax) {
      giaTien = await ChiTietDienThoai.find({
        giaTien: { $gte: Number(GiaMin), $lte: Number(GiaMax) },
      })
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
    } else if (GiaMin) {
      giaTien = await ChiTietDienThoai.find({
        giaTien: { $gte: Number(GiaMin) },
      })
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
    } else if (GiaMax) {
      giaTien = await ChiTietDienThoai.find({
        giaTien: { $lte: Number(GiaMax) },
      })
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
    } else {
      giaTien = await ChiTietDienThoai.find()
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
    }

    const result = await Promise.all(
      giaTien.map(async (item) => {
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return { chiTietDienThoai: item, danhGias };
      })
    );

    res.json(result);
  } catch (error) {
    console.log(error);
    res.status(500).json({ error: error.message });
  }
});

router.get("/filterMoreChiTietDienThoai", async (req, res) => {
  try {
    const { Gia, RAM } = req.query;
    let query = {};

    // Kiểm tra nếu có yêu cầu lọc theo giá
    if (Gia) {
      query.giaTien = Number(Gia);
    }

    // Kiểm tra nếu có yêu cầu lọc theo RAM
    if (RAM) {
      // Tìm id của RAM từ schema Ram
      const ramInfo = await Ram.findOne({ RAM: Number(RAM) });
      if (ramInfo) {
        query["maRam._id"] = ramInfo._id;
      }
    }

    // Kiểm tra nếu có yêu cầu lọc theo dung lượng
    // if (DungLuong) {
    //   // Tìm id của DungLuong từ schema DungLuong
    //   const dungLuongInfo = await DungLuong.findOne({
    //     boNho: Number(DungLuong),
    //   });
    //   if (dungLuongInfo) {
    //     query["maDungLuong._id"] = dungLuongInfo._id;
    //   }
    // }

    let giaTien = await ChiTietDienThoai.find(query);

    res.json({ giaTien });
  } catch (error) {
    console.log(error);
    res.status(500).json({ error: error.message });
  }
});

router.get("/filterBoNhoChiTietDienThoai", async (req, res) => {
  try {
    let boNho = req.query.boNho;

    if (!boNho) {
      return res
        .status(400)
        .json({ message: "Vui lòng cung cấp dung lượng Dung lượng." });
    }

    boNho = boNho.split(",").map((value) => parseInt(value.trim()));

    const dienThoai = await ChiTietDienThoai.find({})
      .populate("maRam")
      .populate("maMau")
      .populate({
        path: "maDienThoai",
        populate: [
          { path: "maCuaHang", model: "cuaHang" },
          { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
          { path: "maHangSX", model: "hangSanXuat" },
        ],
      })
      .populate({
        path: "maDungLuong",
        match: { boNho: { $in: boNho } },
      })
      .exec();

    // Filter out products with no detailed specifications
    const dienThoaiFiltered = dienThoai.filter((dt) => dt.maDungLuong !== null);

    // Transform the response to match the specified format
    const transformedResponse = await Promise.all(
      dienThoaiFiltered.map(async (item) => {
        // Retrieve danhGias for each item
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return {
          chiTietDienThoai: item,
          danhGias: danhGias,
        };
      })
    );

    res.json(transformedResponse);
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
  }
});

router.get("/filterRamChiTietDienThoai", async (req, res) => {
  try {
    // Lấy dung lượng RAM từ query string
    let Ram = req.query.Ram;

    // Kiểm tra nếu dung lượng RAM được chỉ định trong query
    if (!Ram) {
      return res
        .status(400)
        .json({ message: "Vui lòng cung cấp dung lượng RAM." });
    }

    // Xử lý dungLuongRAM để tách thành mảng các giá trị
    Ram = Ram.split(",").map((value) => parseInt(value.trim()));

    // Tìm các điện thoại có dung lượng RAM phù hợp
    const dienThoai = await ChiTietDienThoai.find({})
      .populate("maDungLuong")
      .populate("maMau")
      .populate({
        path: "maDienThoai",
        populate: [
          { path: "maCuaHang", model: "cuaHang" },
          { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
          { path: "maHangSX", model: "hangSanXuat" },
        ],
      })
      .populate({
        path: "maRam",
        match: { RAM: { $in: Ram } },
      })
      .exec();

    // Lọc ra các điện thoại có thông tin về dung lượng RAM phù hợp
    const dienThoaiFiltered = dienThoai.filter((dt) => dt.maRam !== null);
    // Transform the response to match the specified format
    const transformedResponse = await Promise.all(
      dienThoaiFiltered.map(async (item) => {
        // Retrieve danhGias for each item
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return {
          chiTietDienThoai: item,
          danhGias: danhGias,
        };
      })
    );
    res.json(transformedResponse);
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
  }
});

//sắp xếp theo giá cao - thấp

// router.get("/sapxepGiaCao-Thap", async (req, res) => {
//   try {
//     const data = await ChiTietDienThoai.find()
//       .sort({ giaTien: -1 })
//       .populate("maRam")
//       .populate("maDungLuong")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       });

//     // Transform the response to match the specified format
//     const transformedResponse = await Promise.all(
//       data.map(async (item) => {
//         // Retrieve danhGias for each item
//         const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
//           .populate("idKhachHang")
//           .populate({
//             path: "idChiTietDienThoai",
//             populate: [
//               {
//                 path: "maDienThoai",
//                 model: "dienthoai",
//                 populate: [
//                   { path: "maCuaHang", model: "cuaHang" },
//                   { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//                   { path: "maHangSX", model: "hangSanXuat" },
//                 ],
//               },
//               { path: "maMau", model: "mau" },
//               { path: "maDungLuong", model: "dungluong" },
//               { path: "maRam", model: "ram" },
//             ],
//           });
//         return {
//           chiTietDienThoai: item,
//           danhGias: danhGias,
//         };
//       })
//     );

//     res.json(transformedResponse);
//   } catch (err) {
//     res.status(500).json({ message: err.message });
//   }
// });

router.get("/sapxepGiaCao-Thap", async (req, res) => {
  try {
    const data = await ChiTietDienThoai.find()
      .sort({ giaTien: -1 })
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

    // Transform the response to match the specified format
    const transformedResponse = await Promise.all(
      data.map(async (item) => {
        // Check if maUuDai is not null and perform the calculation
        if (item.maDienThoai.maUuDai != null) {
          const giamGia = parseInt(item.maDienThoai.maUuDai.giamGia);
          item.giaTien = parseInt(item.giaTien / (giamGia / 100)); // Chuyển đổi giaTien thành kiểu integer
        }

        // Retrieve danhGias for each item
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return {
          chiTietDienThoai: item,
          danhGias: danhGias,
        };
      })
    );

    res.json(transformedResponse);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

//sắp xếp theo giá thấp - cao
// router.get("/sapxepGiaThap-Cao", async (req, res) => {
//   try {
//     const data = await ChiTietDienThoai.find()
//       .sort({ giaTien: 1 })
//       .populate("maRam")
//       .populate("maDungLuong")
//       .populate("maMau")
//       .populate({
//         path: "maDienThoai",
//         populate: [
//           { path: "maCuaHang", model: "cuaHang" },
//           { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//           { path: "maHangSX", model: "hangSanXuat" },
//         ],
//       });

//     // Transform the response to match the specified format
//     const transformedResponse = await Promise.all(
//       data.map(async (item) => {
//         // Retrieve danhGias for each item
//         const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
//           .populate("idKhachHang")
//           .populate({
//             path: "idChiTietDienThoai",
//             populate: [
//               {
//                 path: "maDienThoai",
//                 model: "dienthoai",
//                 populate: [
//                   { path: "maCuaHang", model: "cuaHang" },
//                   { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
//                   { path: "maHangSX", model: "hangSanXuat" },
//                 ],
//               },
//               { path: "maMau", model: "mau" },
//               { path: "maDungLuong", model: "dungluong" },
//               { path: "maRam", model: "ram" },
//             ],
//           });;
//         return {
//           chiTietDienThoai: item,
//           danhGias: danhGias,
//         };
//       })
//     );

//     res.json(transformedResponse);
//   } catch (err) {
//     res.status(500).json({ message: err.message });
//   }
// });
router.get("/sapxepGiaThap-Cao", async (req, res) => {
  try {
    const data = await ChiTietDienThoai.find()
      .sort({ giaTien: 1 })
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

    // Transform the response to match the specified format
    const transformedResponse = await Promise.all(
      data.map(async (item) => {
        // Check if maUuDai is not null and perform the calculation
        if (item.maDienThoai.maUuDai != null) {
          const giamGia = parseInt(item.maDienThoai.maUuDai.giamGia);
          item.giaTien = parseInt(item.giaTien / (giamGia / 100)); // Chuyển đổi giaTien thành kiểu integer
        }

        // Retrieve danhGias for each item
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return {
          chiTietDienThoai: item,
          danhGias: danhGias,
        };
      })
    );

    res.json(transformedResponse);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

//ưu đãi hot
router.get("/uuDaiHot", async (req, res) => {
  try {
    const data = await ChiTietDienThoai.find()
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

    // Lọc các bản ghi mà maUuDai là null hoặc trangThai của maUuDai là "Không Hoạt Động"
    const filteredData = data.filter((item) => {
      return (
        item.maDienThoai.maUuDai &&
        item.maDienThoai.maUuDai.trangThai === "Hoạt động"
      );
    });

    // Ép kiểu trường giamGia từ string sang integer
    filteredData.forEach((item) => {
      if (item.maDienThoai.maUuDai && item.maDienThoai.maUuDai.giamGia) {
        item.maDienThoai.maUuDai.giamGia = parseInt(
          item.maDienThoai.maUuDai.giamGia
        );
      }
    });

    // Sắp xếp theo giamGia từ cao đến thấp
    filteredData.sort((a, b) => {
      const giamGiaA = a.maDienThoai.maUuDai
        ? a.maDienThoai.maUuDai.giamGia
        : 0;
      const giamGiaB = b.maDienThoai.maUuDai
        ? b.maDienThoai.maUuDai.giamGia
        : 0;
      return giamGiaB - giamGiaA;
    });

    // Transform the response to match the specified format
    const transformedResponse = await Promise.all(
      filteredData.map(async (item) => {
        // Retrieve danhGias for each item
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return {
          chiTietDienThoai: item,
          danhGias: danhGias,
        };
      })
    );

    res.json(transformedResponse);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

router.get("/filterDienThoai", async (req, res) => {
  try {
    let { Ram, boNho } = req.query;

    if (!Ram || !boNho) {
      return res.status(400).json({
        message: "Vui lòng cung cấp cả dung lượng RAM và dung lượng bộ nhớ.",
      });
    }

    Ram = Ram.split(",").map((value) => parseInt(value.trim()));
    boNho = boNho.split(",").map((value) => parseInt(value.trim()));

    const dienThoai = await ChiTietDienThoai.find({})
      .populate("maDungLuong")
      .populate("maMau")
      .populate({
        path: "maDienThoai",
        populate: [
          { path: "maCuaHang", model: "cuaHang" },
          { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
          { path: "maHangSX", model: "hangSanXuat" },
        ],
      })
      .populate({
        path: "maRam",
        match: { RAM: { $in: Ram } },
      })
      .populate({
        path: "maDungLuong",
        match: { boNho: { $in: boNho } },
      })
      .exec();

    const filteredDienThoai = dienThoai.filter(
      (dt) => dt.maRam !== null && dt.maDungLuong !== null
    );

    const transformedResponse = await Promise.all(
      filteredDienThoai.map(async (item) => {
        const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
          .populate("idKhachHang")
          .populate({
            path: "idChiTietDienThoai",
            populate: [
              {
                path: "maDienThoai",
                model: "dienthoai",
                populate: [
                  { path: "maCuaHang", model: "cuaHang" },
                  { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                  { path: "maHangSX", model: "hangSanXuat" },
                ],
              },
              { path: "maMau", model: "mau" },
              { path: "maDungLuong", model: "dungluong" },
              { path: "maRam", model: "ram" },
            ],
          });
        return {
          chiTietDienThoai: item,
          danhGias: danhGias,
        };
      })
    );

    res.json(transformedResponse);
  } catch (err) {
    console.error(err);
    res
      .status(500)
      .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
  }
});

//lọc điện thoại
router.get("/filterChiTietDienThoai", async (req, res) => {
    try {
        const {
            GiaMin,
            GiaMax,
            Ram,
            boNho,
            sortByPrice,
            uuDaiHot,
            maHangSanXuat,
            sortDanhGia
        } = req.query;

        let filter = {};
        let sort = {};

        // Lọc theo giá
        if (GiaMin && GiaMax) {
            filter.giaTien = { $gte: Number(GiaMin), $lte: Number(GiaMax) };
        } else if (GiaMin) {
            filter.giaTien = { $gte: Number(GiaMin) };
        } else if (GiaMax) {
            filter.giaTien = { $lte: Number(GiaMax) };
        }

        // Sắp xếp theo giá
        if (sortByPrice === "asc") {
            sort.giaTien = 1;
        } else if (sortByPrice === "desc") {
            sort.giaTien = -1;
        }

        let dienThoai;
        dienThoai = await ChiTietDienThoai.find(filter)
            .sort(sort)
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
        if (maHangSanXuat){
            dienThoai = dienThoai.filter(item => item.maDienThoai.maHangSX._id.toString() === maHangSanXuat)
        }

        if (uuDaiHot === "true") {
            dienThoai = dienThoai.filter(item => item.maDienThoai.maUuDai !== null)
                .sort((a, b) => {
                    // Sắp xếp theo mã ưu đãi giảm dần
                    return b.maDienThoai.maUuDai.giamGia - a.maDienThoai.maUuDai.giamGia;
                });

        }
        if (Ram){
            const [minRam, maxRam] = Ram.split(",").map((value) => parseInt(value.trim()));
            dienThoai = dienThoai.filter(item =>
                // log(item)
                item.maRam.RAM >= minRam && item.maRam.RAM <= maxRam
            )

        }
        if (boNho){
            const [minBoNho, maxBoNho] = boNho.split(",").map((value) => parseInt(value.trim()));
            dienThoai = dienThoai.filter(item => item.maDungLuong.boNho >= minBoNho && item.maDungLuong.boNho <= maxBoNho )

        }

        const transformedResponse = await Promise.all(
            dienThoai.map(async (item) => {
                const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
                    .populate("idKhachHang")
                    .populate({
                        path: "idChiTietDienThoai",
                        populate: [
                            {
                                path: "maDienThoai",
                                model: "dienthoai",
                                populate: [
                                    { path: "maCuaHang", model: "cuaHang" },
                                    { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                                    { path: "maHangSX", model: "hangSanXuat" },
                                ],
                            },
                            { path: "maMau", model: "mau" },
                            { path: "maDungLuong", model: "dungluong" },
                            { path: "maRam", model: "ram" },
                        ],
                    });
                const averageRating = calculateAverageRating(danhGias);
                return {
                    chiTietDienThoai: item,
                    danhGias: danhGias,
                    tbDiemDanhGia: averageRating
                };
            })
        );
        if (sortDanhGia === "true"){
            transformedResponse.sort((a, b) => b.tbDiemDanhGia - a.tbDiemDanhGia);
        }

        res.json(transformedResponse);
    } catch (err) {
        console.error(err);
        res
            .status(500)
            .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
    }
});
function calculateAverageRating(danhGias) {
    if (danhGias.length === 0) return 0;

    const totalRating = danhGias.reduce((sum, danhGia) => sum + danhGia.diemDanhGia, 0);
    return totalRating / danhGias.length;
}

router.get("/searchDienThoaiVaCuaHang", async (req, res) => {
  try {
    const {
      GiaMin,
      GiaMax,
      Ram,
      boNho,
      sortByPrice,
      uuDaiHot,
      maHangSanXuat,
      sortDanhGia,
      search
    } = req.query;

    let filter = {};
    let sort = {};

    // Lọc theo giá
    if (GiaMin && GiaMax) {
      filter.giaTien = { $gte: Number(GiaMin), $lte: Number(GiaMax) };
    } else if (GiaMin) {
      filter.giaTien = { $gte: Number(GiaMin) };
    } else if (GiaMax) {
      filter.giaTien = { $lte: Number(GiaMax) };
    }

    // Sắp xếp theo giá
    if (sortByPrice === "asc") {
      sort.giaTien = 1;
    } else if (sortByPrice === "desc") {
      sort.giaTien = -1;
    }

    let dienThoai;
    let cuaHangs = [];
    dienThoai = await ChiTietDienThoai.find(filter)
        .sort(sort)
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
    if (search){
      dienThoai = dienThoai.filter(item => item.maDienThoai.tenDienThoai.toLowerCase().includes(search.toLowerCase()))
      cuaHangs = await CuaHang.find({username: { $regex: search, $options: "i" }})
          .select('_id username diaChi')
    }
    if (maHangSanXuat){
      dienThoai = dienThoai.filter(item => item.maDienThoai.maHangSX._id.toString() === maHangSanXuat)
    }

    if (uuDaiHot === "true") {
      dienThoai = dienThoai.filter(item => item.maDienThoai.maUuDai !== null)
          .sort((a, b) => {
            // Sắp xếp theo mã ưu đãi giảm dần
            return b.maDienThoai.maUuDai.giamGia - a.maDienThoai.maUuDai.giamGia;
          });
          
    }
    if (Ram){
      const [minRam, maxRam] = Ram.split(",").map((value) => parseInt(value.trim()));
      dienThoai = dienThoai.filter(item =>
          // log(item)
          item.maRam.RAM >= minRam && item.maRam.RAM <= maxRam
      )

    }
    if (boNho){
      const [minBoNho, maxBoNho] = boNho.split(",").map((value) => parseInt(value.trim()));
      dienThoai = dienThoai.filter(item => item.maDungLuong.boNho >= minBoNho && item.maDungLuong.boNho <= maxBoNho )

    }

    const transformedResponse = await Promise.all(
        dienThoai.map(async (item) => {
          const danhGias = await DanhGia.find({ idChiTietDienThoai: item._id })
              .populate("idKhachHang")
              .populate({
                path: "idChiTietDienThoai",
                populate: [
                  {
                    path: "maDienThoai",
                    model: "dienthoai",
                    populate: [
                      { path: "maCuaHang", model: "cuaHang" },
                      { path: "maUuDai", model: "uudai", populate: "maCuaHang" },
                      { path: "maHangSX", model: "hangSanXuat" },
                    ],
                  },
                  { path: "maMau", model: "mau" },
                  { path: "maDungLuong", model: "dungluong" },
                  { path: "maRam", model: "ram" },
                ],
              });
          const averageRating = calculateAverageRating(danhGias);
          return {
            chiTietDienThoai: item,
            danhGias: danhGias,
            tbDiemDanhGia: averageRating
          };
        })
    );
    if (sortDanhGia === "true"){
      transformedResponse.sort((a, b) => b.tbDiemDanhGia - a.tbDiemDanhGia);
    }

    res.json({dienThoais: transformedResponse, cuaHangs});
  } catch (err) {
    console.error(err);
    res
        .status(500)
        .json({ message: "Đã xảy ra lỗi khi lấy danh sách điện thoại." });
  }
});
function calculateAverageRating(danhGias) {
  if (danhGias.length === 0) return 0;

  const totalRating = danhGias.reduce((sum, danhGia) => sum + danhGia.diemDanhGia, 0);
  return totalRating / danhGias.length;
}
module.exports = router;
