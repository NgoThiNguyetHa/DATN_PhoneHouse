var express = require("express");
var router = express.Router();

/* GET home page. */
router.get("/", function (req, res, next) {
  res.render("index", { title: "Express" });
});

var sock = require("../socket_server");

router.get("/notify", (req, res, next) => {
  sock.io.emit("new msg", "Noi dung thong bao");
  res.end();
});

module.exports = router;
