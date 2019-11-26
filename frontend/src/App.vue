<template>
  <v-app>
    <v-navigation-drawer v-model="drawer" app>
      <v-list dense>
        <v-list-item @click="routePage('home')">
          <v-list-item-action>
            <v-icon>home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="routePage('about')">
          <v-list-item-action>
            <v-icon>contact_mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>About</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item @click="routePage('orders')">
          <v-list-item-action>
            <v-icon>cart-outline</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Orders</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar app color="indigo" dark>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title class="headline text-uppercase">
        <span>Falcon</span>
        <span class="font-weight-light">GARAGE</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <span class="mr-2">v1.0</span>
    </v-app-bar>

    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col class="text-center">
            <router-view></router-view>
          </v-col>
        </v-row>
      </v-container>
    </v-content>

    <v-footer app color="indigo" dark>
      <span class="white--text">&copy; 2019</span>
    </v-footer>
  </v-app>
</template>

<script>

import Router from './router.js';
const router = Router;

export default {
  name: 'App',
  props: {
    source: String,
  },
  data: () => ({
    drawer: true
  }),

  methods: {
    toggleDrawer() {
      this.drawer = !this.drawer
    },
    routePage(name) {
      router.push({ name: name })
    }
  }
};
</script>
