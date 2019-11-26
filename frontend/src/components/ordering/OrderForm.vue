<template>
    <div class="order">
        <v-form>
            <v-container>
                <v-row> 
                    <v-col class="d-flex">
                         <v-container>
                            <h2>Order Details</h2>
                            <v-row dense >
                                <v-col class="customerName">
                                    <v-text-field id="customerName" v-model="order.customerName" label="Customer Name" required outlined></v-text-field>
                                </v-col>
                            </v-row>
                            <v-row dense>
                                <v-col class="orderType">
                                    <v-text-field id="orderType" v-model="order.type" label="Order Type" required outlined></v-text-field>
                                </v-col>
                            </v-row>
                            <v-row dense>
                                <v-col class="remarks">
                                    <v-textarea
                                        outlined
                                        name="remarks"
                                        label="Remarks"
                                        value=""
                                        height="100"
                                        no-resize="true"
                                        v-model="order.remarks"
                                        ></v-textarea>
                                </v-col>
                            </v-row>

                         </v-container>
                    </v-col>


                    <v-col class="d-flex" cols="12" md="8">
                         <v-container>
                             <h2>Order Items</h2>
                             <v-row v-for="(input, index) in inputs" v-bind:key="input" dense>
                    
                                <v-col cols="12" md="8">
                                    <v-select :items="products" label="Product" v-model="input.product" outlined=""></v-select>
                                </v-col>

                                <v-col cols="12" md="1">
                                    <v-text-field id="quantity" v-model="input.quantity" label="Qty" required outlined></v-text-field>
                                </v-col>

                                <v-col class="subtotalCost" cols="12" md="2">
                                    <v-alert text color="green" icon="mdi-currency-php">{{ subtotalAmount }}</v-alert>
                                </v-col>

                                <v-col class="deleteBtn" cols="12" md="1">
                                    <v-icon @click="deleteRow(index)" large>mdi-trash-can-outline</v-icon>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-tooltip bottom>
                                    <template v-slot:activator="{ on }">
                                        <v-icon class="addProductItem" @click="addRow" large color="green">mdi-plus-thick</v-icon>
                                    </template>
                                    <span>Add a new product</span>
                                </v-tooltip>
                            </v-row>

                            <v-row>
                                <v-col cols="12" md="5"><h2>Total Amount: </h2></v-col>
                                <v-col cols="12" md="7">
                                    <v-alert class="totalAmount" text color="green" icon="mdi-currency-php" >{{ totalAmount }}</v-alert>
                                </v-col>
                            </v-row>
                         </v-container>
                    </v-col>
                </v-row>
            
                <v-divider></v-divider>
                
                <v-row>
                    <v-col cols="12" sm="6" md="12">
                         <div class="submitBtn">
                            <v-btn large color="primary" @click="createOrder">Submit</v-btn>
                        </div>
                    </v-col>
                </v-row>
            </v-container>
        </v-form>
    </div>
</template>

<script>
    import OrderService from '../../services/OrderService';

    export default {
        data () {
            return {
                inputs: [ {product: "door", quantity: 5} ],
                order: {
                    orderId: '1',
                    type: '',
                    customerName: '',
                    createdDate: '',
                    remarks: ''
                }
            }
        },
        computed: {
            subtotalAmount () {
                return "100.00";
            },
            totalAmount () {
                return "500.00"
            }
        },
        methods: {
            showAlert () {

            },
            addRow() {
                this.inputs.push({
                    product: '',
                    quantity: ''
                })
            },
            deleteRow(index) {
                this.inputs.splice(index,1)
            },
            async createOrder () {
                try {
                    this.orders = await OrderService.insertOrder(this.order);
                } catch (err) {
                    this.error = err.message;
                }
            }
        }
    }
</script>

<style>

@import url('https://fonts.googleapis.com/css?family=Raleway&display=swap');

.submitBtn {
    text-align: right;
}

.deleteBtn {
    text-align: left;
}

.addProductItem {
    margin-left: 12px;
    margin-bottom: 12px;
}

.totalAmount {
    
}

.order {
    font-family: 'Raleway', sans-serif;
}

</style>