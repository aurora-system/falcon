const express = require('express');
const router = express.Router();
const mongoose = require('mongoose');

// load Product Model
require('../models/Order');
const Order = mongoose.model('orders');

// list all products
router.get('/', function(req, res, next) {
  Order.find()
    .sort({name: 'asc'})
    .then(orders => {
        console.log(orders);
        res.json(orders);
    })
});

router.post('/', function(req, res, next) {
    let newOrder = {orderId: 2, name: 'Sample Order 2'};
    new ProductCategory(newOrder)
        .save()
        .then(ord => {
            console.log(ord);
            res.json(ord)
        })
});

module.exports = router;