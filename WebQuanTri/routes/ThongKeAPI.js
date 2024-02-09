var express = require("express");
var router = express.Router();
const mongoose = require("mongoose");
const moment = require('moment');

require("../models/HoaDon");
require("../models/ChiTietHoaDon");
require("../models/DienThoai");
require("../models/HangSanXuat");

const HoaDon = mongoose.model("hoaDon");
const ChiTietHoaDon = mongoose.model("chiTietHoaDon");
const DienThoai = mongoose.model("dienthoai");
const HangSX = mongoose.model("hangSanXuat");

router.get("/", function (req, res, next) {
  res.send("respond with a resource");
});

router.get("/getDienThoaiHotNhat", async (req, res) => {
  try {
    // Truy vấn dữ liệu từ bảng Hóa Đơn và nhóm theo mã điện thoại
    const dienThoaiDuocMuaNhieu = await ChiTietHoaDon.aggregate([
      {
        $group: {
          _id: "$maDienThoai",
          soLuong: { $sum: "$soLuong" },
        },
      },
      {
        $sort: { soLuong: -1 }, // Sắp xếp theo số lượng mua giảm dần
      },
    ]).exec();
    console.log("31: ", dienThoaiDuocMuaNhieu);
    // Lấy thông tin chi tiết của các điện thoại từ bảng Điện Thoại
    const dienThoaiDetails = await DienThoai.populate(dienThoaiDuocMuaNhieu, {
      path: "_id",
      select: "tenDienThoai giaTien",
    });

    res.status(200).json({ dienThoaiDuocMuaNhieu: dienThoaiDetails });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get("/getSoLuongSanPham-CuaHang/:id", async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const hangSX = await HangSX.find({ maCuaHang: idCuaHang })
      .populate("maCuaHang", "_id")
      .populate("maCuaHang");
    const dienThoais = [];
    for (const hang of hangSX) {
      const dienThoai = await DienThoai.find({ maHangSX: hang._id })
        .populate("maHangSX", "_id")
        .populate("maHangSX");
      if (dienThoai) {
        dienThoais.push(...dienThoai);
      }
    }
    res.status(200).json({ soLuong: dienThoais.length });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get(
  "/getSoLuongDonHangTheoTrangThai/:id/:trangThaiNhanHang",
  async (req, res) => {
    try {
      const trangThaiNhanHang = req.params.trangThaiNhanHang;
      const maCuaHang = req.params.id;

      const slHoaDon = await HoaDon.countDocuments({
        trangThaiNhanHang,
        maCuaHang,
      });
      res.json({ soLuong: slHoaDon });
    } catch (error) {
      res.status(500).json({ error: error.message });
    }
  }
);

router.get("/thongKeSoLuongKhachHang/:id/:ngay", async (req, res) => {
  try {
    const idCuaHang = req.params.id;
    const ngay = new Date(req.params.ngay);
    console.log(ngay);
    const thongKe = await HoaDon.aggregate([
      {
        $match: {
          maCuaHang: new mongoose.Types.ObjectId(idCuaHang),
          ngayTao: { $gte: ngay, $lt: new Date(ngay.getTime() + 86400000) },
        },
      },
      {
        $group: {
          _id: "$maKhachHang",
          soLuongHoaDon: { $sum: 1 },
        },
      },
      {
        $group: {
          _id: null,
          soLuongKhachHang: { $sum: 1 },
        },
      },
    ]);
    const soLuongKhachHang =
      thongKe.length > 0 ? thongKe[0].soLuongKhachHang : 0;

    res.json({ soLuongKhachHang });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get("/top10-dien-thoai-theo-ngay/:tuNgay/:denNgay", async (req, res) => {
  try {
    const tuNgay = req.params.tuNgay;
    const denNgay = req.params.denNgay;
    const result = await ChiTietHoaDon.aggregate([
      {
        $match: {
          createdAt: {
            $gte: new Date(tuNgay),
            $lt: new Date(denNgay),
          },
        },
      },
      {
        $group: {
          _id: "$maDienThoai",
          soLuongMua: { $sum: "$soLuong" },
        },
      },
      {
        $sort: { soLuongMua: -1 },
      },
      {
        $limit: 10,
      },
    ]);

    // Lấy thông tin chi tiết điện thoại từ bảng dienthoai
    const top10DienThoai = await Promise.all(
      result.map(async (item) => {
        const dienThoaiInfo = await DienThoai.findById(item._id);
        return {
          maDienThoai: item._id,
          tenDienThoai: dienThoaiInfo.tenDienThoai,
          soLuongMua: item.soLuongMua,
        };
      })
    );

    res.json(top10DienThoai);
  } catch (error) {
    console.error(error);
    res.status(500).send("Internal Server Error");
  }
});

router.get(
  "/top-10-dien-thoai-mua-nhieu/:startDate/:endDate",
  async (req, res) => {
    try {
      // Lấy ngày bắt đầu và kết thúc từ URL params
      const startDate = new Date(req.params.startDate);
      const endDate = new Date(req.params.endDate);

      // Sử dụng aggregation framework để lấy top 10 điện thoại được mua nhiều nhất
      const topPhones = await ChiTietHoaDon.aggregate([
        {
          $match: {
            // Lọc các chi tiết hóa đơn trong khoảng ngày
            $and: [
              { giaTien: { $exists: true } },
              { "maHoaDon.ngayTao": { $gte: startDate, $lte: endDate } },
            ],
          },
        },
        {
          $group: {
            // Nhóm theo mã điện thoại và tính tổng số lượng
            _id: "$maDienThoai",
            totalQuantity: { $sum: "$soLuong" },
          },
        },
        {
          $sort: {
            // Sắp xếp theo tổng số lượng giảm dần
            totalQuantity: -1,
          },
        },
        {
          $limit: 10, // Chọn top 10
        },
      ]);

      // Lấy thông tin chi tiết điện thoại từ mã điện thoại
      const topPhonesDetails = await Promise.all(
        topPhones.map(async (phone) => {
          const phoneDetails = await DienThoai.findById(phone._id);
          return {
            ...phoneDetails.toJSON(),
            totalQuantity: phone.totalQuantity,
          };
        })
      );

      res.json(topPhonesDetails);
    } catch (error) {
      console.error(error);
      res.status(500).send("Internal Server Error");
    }
  }
);

router.get("/soLuongHoaDon/:trangThaiNhanHang/:maCuaHang/:ngayTao",
  async (req, res) => {
    try {
      const maCuaHang = req.params.maCuaHang;
      const ngayTao = req.params.ngayTao;
      const trangThaiNhanHang = req.params.trangThaiNhanHang;
      const result = await HoaDon.aggregate([
        {
          $match: {
            maCuaHang: new mongoose.Types.ObjectId(maCuaHang),
            ngayTao: ngayTao,
            trangThaiNhanHang: trangThaiNhanHang,
          },
        },
        {
          $group: {
            _id: "$trangThaiNhanHang",
            count: { $sum: 1 },
          },
        },
      ]);
      res.json(result);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Internal Server Error" });
    }
  }
);

router.get('/soLuongKhachHang/:maCuaHang/:ngayTao', async (req, res) => {
    try {
      const maCuaHang = req.params.maCuaHang;
      const ngayTao = req.params.ngayTao;
      const result = await HoaDon.aggregate([
        {
          $match: {
            maCuaHang: new mongoose.Types.ObjectId(maCuaHang),
            ngayTao: ngayTao,
          },
        },
        {
          $group: {
            _id: null,
            uniqueMaKhachHang: { $addToSet: "$maKhachHang" },
            total: { $sum: 1 },
          },
        },
        {
          $project: {
            _id: 0,
            uniqueMaKhachHang: { $size: "$uniqueMaKhachHang" },
            total: 1,
          },
        },
      ]);
      res.json(result[0]);
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "Lỗi server" });
    }
});

router.get('/soLuongSanPham/:maCuaHang/:ngayTao', async (req, res) => {
  try {
    const ngayTao = req.params.ngayTao;
    const maCuaHang = req.params.maCuaHang;
    const conditions = { trangThaiNhanHang: 'Đã giao' };
    if (ngayTao) {
      conditions.ngayTao = ngayTao;
    }
    if (maCuaHang) {
      conditions.maCuaHang = new mongoose.Types.ObjectId(maCuaHang);
    }
    const hoaDonDaGiao = await HoaDon.find(conditions);
    let soLuongDaBan = 0;
    for (const hoaDon of hoaDonDaGiao) {
      const chiTietHoaDon = await ChiTietHoaDon.find({ maHoaDon: hoaDon._id });
      const uniqueSanPham = new Set(chiTietHoaDon.map(item => item.maDienThoai.toString()));
      soLuongDaBan += uniqueSanPham.size;
    }
    res.json({ soLuongDaBan });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
});

router.get('/thongKeTongTien/:trangThaiNhanHang/:maCuaHang/:ngayTao', async (req, res) => {
    try {
        const maCuaHang = req.params.maCuaHang;
        const ngayTao = req.params.ngayTao;
        const trangThaiNhanHang = req.params.trangThaiNhanHang;
        const tongTien = await HoaDon.aggregate([
            {
                $match: {
                    maCuaHang: new mongoose.Types.ObjectId(maCuaHang),
                    ngayTao: ngayTao,
                    trangThaiNhanHang: trangThaiNhanHang 
                }
            },
            {
                $group: {
                    _id: null,
                    tongTien: { $sum: { $toInt: '$tongTien' } }
                }
            }
        ]);

        res.json({ tongTien: tongTien.length > 0 ? tongTien[0].tongTien : 0 });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Có lỗi xảy ra' });
    }
});

function convertDateFormat(inputDate) {
  const [day, month, year] = inputDate.split('-');
  return `${year}-${month}-${day}`;
}

const fromDate = convertDateFormat('04-04-2024'); // Kết quả: '2024-04-04'
const toDate = convertDateFormat('24-04-2024');
//top 10 theo tháng
router.get('/top10ProductWithDay', async (req, res) => {
  try {


    const fromDateObj = new Date(fromDate);
    const toDateObj = new Date(toDate);

    // Tìm các hóa đơn đã giao thành công trong khoảng thời gian
    const hoaDons = await HoaDon.find({
      trangThaiNhanHang: 'Đã giao',
      ngayTao: { $gte: fromDateObj, $lte: toDateObj },
    });

    // Tạo một đối tượng để lưu số lượng bán của mỗi sản phẩm
    const sanPhamBanChay = {};

    // Duyệt qua từng hóa đơn và chi tiết hóa đơn để tính số lượng bán của từng sản phẩm
    for (const hoaDon of hoaDons) {
      const chiTietHoaDons = await ChiTietHoaDon.find({ maHoaDon: hoaDon._id });

      for (const chiTietHoaDon of chiTietHoaDons) {
        const maDienThoai = chiTietHoaDon.maDienThoai.toString();
        if (sanPhamBanChay[maDienThoai]) {
          sanPhamBanChay[maDienThoai] += chiTietHoaDon.soLuong;
        } else {
          sanPhamBanChay[maDienThoai] = chiTietHoaDon.soLuong;
        }
      }
    }

    // Sắp xếp mảng theo số lượng bán giảm dần và lấy top 10
    const top10SanPham = Object.entries(sanPhamBanChay)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 10);

    // Lấy thông tin chi tiết của từng sản phẩm từ cơ sở dữ liệu
    const top10SanPhamInfo = await Promise.all(
      top10SanPham.map(async ([maDienThoai, soLuongBan]) => {
        const dienThoai = await DienThoai.findById(maDienThoai);
        return {
          idSanPham: dienThoai._id,
          giaTien: dienThoai.giaTien,
          soLuotBan: soLuongBan,
        };
      })
    );

    // Trả về kết quả
    res.json(top10SanPhamInfo);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
});


router.get('/top10SanPhamBanChay', async (req, res) => {
  try {
    // Tìm các hóa đơn đã giao thành công trong khoảng thời gian
    const hoaDons = await HoaDon.find({
      trangThaiNhanHang: 'Đã giao',
    });

    // Tạo một đối tượng để lưu số lượng bán của mỗi sản phẩm
    const sanPhamBanChay = {};

    // Duyệt qua từng hóa đơn và chi tiết hóa đơn để tính số lượng bán của từng sản phẩm
    for (const hoaDon of hoaDons) {
      const chiTietHoaDons = await ChiTietHoaDon.find({ maHoaDon: hoaDon._id });

      for (const chiTietHoaDon of chiTietHoaDons) {
        const maDienThoai = chiTietHoaDon.maDienThoai.toString();
        if (sanPhamBanChay[maDienThoai]) {
          sanPhamBanChay[maDienThoai] += chiTietHoaDon.soLuong;
        } else {
          sanPhamBanChay[maDienThoai] = chiTietHoaDon.soLuong;
        }
      }
    }

    // Sắp xếp mảng theo số lượng bán giảm dần và lấy top 10
    const top10SanPham = Object.entries(sanPhamBanChay)
      .sort((a, b) => b[1] - a[1])
      .slice(0, 10);

    // Lấy thông tin chi tiết của từng sản phẩm từ cơ sở dữ liệu
    const top10SanPhamInfo = await Promise.all(
      top10SanPham.map(async ([maDienThoai, soLuongBan]) => {
        const dienThoai = await DienThoai.findById(maDienThoai);
        return {
          idSanPham: dienThoai._id,
          giaTien: dienThoai.giaTien,
          soLuotBan: soLuongBan,
        };
      })
    );

    // Trả về kết quả
    res.json(top10SanPhamInfo);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
});


router.get('/top10products', async (req, res) => {
    try {
        const { fromDate, toDate } = req.query;

        // Sử dụng aggregation framework để thực hiện phép nhóm và sắp xếp
        const topProducts = await ChiTietHoaDon.aggregate([
            {
                $match: {
                    'maHoaDon.trangThaiNhanHang': 'Đã giao',
                    'maHoaDon.ngayTao': {
                        $gte: new Date(fromDate),
                        $lte: new Date(toDate),
                    },
                },
            },
            {
                $group: {
                    _id: '$maDienThoai',
                    soLuotBan: { $sum: '$soLuong' },
                    giaTien: { $first: '$giaTien' },
                },
            },
            {
                $sort: { soLuotBan: -1 },
            },
            {
                $limit: 10,
            },
        ]);

        // Trả về kết quả
        const result = topProducts.map((product) => ({
            idSanPham: product._id,
            giaTien: product.giaTien,
            soLuotBan: product.soLuotBan,
        }));

        res.json(result);
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

module.exports = router;
