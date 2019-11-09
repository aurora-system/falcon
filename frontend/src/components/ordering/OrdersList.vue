<template>
  <div class="order">
    <v-app id="orderList">
      <v-data-table
        v-model="selected"
        :headers="headers"
        :items="orders"
        :single-select="singleSelect"
        item-key="name"
        show-select
        class="elevation-1"
      >
        <template v-slot:top>
          <v-switch v-model="singleSelect" label="Single select" class="pa-3"></v-switch>
        </template>
      </v-data-table>
    </v-app>
  </div>
</template>

<script>
  export default {
    data () {
    return {
      singleSelect: false,
      selected: [],
      headers: [
        {
          text: 'Order ID',
          align: 'left',
          sortable: false,
          value: 'orderId',
        },
        { text: 'Customer Name', value: 'customerName' },
        { text: 'Product', value: 'product' },
        { text: 'Quantity', value: 'quantity' },
        { text: 'Total Amount', value: 'totalAmount' }
      ],
      orders: [
        {
          orderId: '0001',
          orderType: 'sale',
          customerName: 'Boy Dizon',
          product: 'bumper',
          quantity: 1,
          totalAmount: 2000
        },
        {
          orderId: '0002',
          orderType: 'sale',
          customerName: 'Girl Cristobal',
          product: 'tail lights',
          quantity: 2,
          totalAmount: 3500
        },
        {
          orderId: '0003',
          orderType: 'service',
          customerName: 'Maximo Minero',
          product: 'detailing',
          quantity: 1,
          totalAmount: 5000
        },
        {
          orderId: '0004',
          orderType: 'sale',
          customerName: 'Louis Dizon',
          product: 'Mazda 3 2013 Hatchback',
          quantity: 1,
          totalAmount: 360000
        },
      ],
    }
  },
  }
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Raleway&display=swap');

#orderList {
  font-family: 'Raleway', sans-serif;
}
</style>