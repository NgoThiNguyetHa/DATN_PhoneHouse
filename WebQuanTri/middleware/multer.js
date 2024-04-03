const multer = require('multer');
const path = require('path')
function getRandomInt(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

// const fileFilter = (req, file, cb) => {
//   const fileSize = parseInt(req.headers['content-length']);
//   // if (file.mimetype !== "image/png") {
//   //   return cb(new Error('Chỉ chấp nhận tệp png.'));
//   // }
//   if (fileSize > maxSize) {
//     return cb(new Error('Dung File Lượng Quá Lớn'));
//   }
//   cb(null, true);
// };

// var storage = multer.diskStorage({
//   destination: function (req, file, cb) {
//     cb(null, './uploads');
//   },
//   filename: function (req, file, cb) {
//     // Đặt tên file được upload lên để không bị trùng lặp
//     const originalname = file.originalname;
//     var name = Date.now() + originalname.replace(/[^a-zA-Z0-9.-]/g, '_')
//     cb(null, name);
//   },
// });


const upload = multer({
  storage: multer.memoryStorage(),
  limits: { fileSize: 1000000},
  fileFilter: async function(req, file, cb){
    checkFileType(file, cb)
  }
}).single("image")

const checkFileType = (file, cb) => {
  const fileTypes = /jpeg|jpg|png/;
  const extName = fileTypes.test(path.extname(file.originalname).toLowerCase());
  const mimeType = fileTypes.test(file.mimetype);
  if (mimeType && extName){
    return cb(null, true)
  }else {
    cb("Error")
  }
}


module.exports = {upload};