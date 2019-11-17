const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const OrderSchema = new Schema({
    orderId: {
        type: Number,
        required: true,
    },
    type: {
        type: String,
        required: true
    }
})
mongoose.model('orders', OrderSchema)