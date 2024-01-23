var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser')

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var mauRouter = require('./routes/MauRoutersAPI');
var ramRouter = require('./routes/RamRoutersAPI');
var dungLuongRouter = require('./routes/DungLuongRoutersAPI')
var dienThoaiRouter = require('./routes/DienThoaiRoutersAPI')
var uuDaiRouter = require('./routes/UuDaiRoutersAPI')
var HangSanXuatAPI = require('./routes/HangSanXuatAPI');
var CuaHangAPI = require('./routes/CuaHangAPI');
var DiaChiNhanHangAPI = require('./routes/DiaChiNhanHangAPI');
var HoaDonAPI = require('./routes/HoaDonAPI');
var ChiTietHoaDonAPI = require('./routes/ChiTietHoaDonAPI');
var GioHangAPI = require('./routes/GioHangAPI');
var ChiTietGioHangAPI = require('./routes/ChiTietGioHangAPI');

const mongoose = require('mongoose');
const { error } = require('console');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/maus', mauRouter);
app.use('/rams',ramRouter)
app.use('/dungluongs',dungLuongRouter)
app.use('/dienthoais',dienThoaiRouter)
app.use('/uudais',uuDaiRouter)
app.use('/hangsanxuats' , HangSanXuatAPI) // Hãng sản xuất
app.use('/cuahangs' , CuaHangAPI) // Cửa hàng
app.use('/diachinhanhangs', DiaChiNhanHangAPI) //Địa chỉ nhận hàng
app.use('/hoadons' , HoaDonAPI) // Hóa đơn
app.use('/chitiethoadons', ChiTietHoaDonAPI) //Chi tiết hóa đơn
app.use('/giohangs', GioHangAPI) //Giỏ hàng
app.use('/chitietgiohangs', ChiTietGioHangAPI) //Chi tiết giỏ hàng
// parse application/json
app.use(bodyParser.json())


//connection database mongoodb
const mongoURL= 'mongodb+srv://hoanglong180903:tVppUteM4IrqkvDv@cluster0.2gdloo3.mongodb.net/MyNodejsApp'
mongoose.connect(mongoURL)
.then(() => {
  console.log("connection successfully")
})
.catch((error) => {
  console.log("Error connecting to database")
});


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
const port = process.env.PORT || 8686;
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
