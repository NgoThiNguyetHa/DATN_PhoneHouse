'use strict';
const { Model } = require('sequelize')
const moment = require('moment')
const req = require("express/lib/request");
module.exports = (sequelize, DataTypes) => {
  class Push_Notifications extends Model {
    static associate(models) {
      Push_Notifications.belongsTo(models.user, {
        foreignKey: 'user_id',
        onDelete: 'CASCADE'
      })
    }
  }
  Push_Notifications.init({
    user_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    fcm_token:{
      type: DataTypes.TEXT,
      allowNull: false,
    },
    createedAt:{
      type: DataTypes.BIGINT,
      allowNull: false,
    },
    updatedAt:{
      type: DataTypes.BIGINT,
      allowNull: false,
    }
  })
}
