
// #ifndef VUE3
import Vue from 'vue'
import App from './App'
// 利用第三方包发起网络请求
import { $http } from '@escook/request-miniprogram'

uni.$http = $http

$http.baseUrl = 'http://localhost:8090'
Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
import App from './App.vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif