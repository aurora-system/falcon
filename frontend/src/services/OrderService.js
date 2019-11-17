import axios from 'axios'

const url = "api/orders";

class OrderService {
    
    static getOrders() {
        return new Promise(async (resolve, reject) => {
            try {
                const res = await axios.get(url);
                const data = res.data;
                resolve(data.map(order => ({
                    ...post,
                    createdAt: new Date(order.createdAt)
                })))
            } catch (err) {
                reject(err)
            }
        })
    }
    // Create Order
    static insertOrder(text) {
        return axios.post(url, {text});
    }
    // Delete Order
    static deleteOrder(id) {
        return axios.delete(`${url}/${id}`)
    }
}