const Axios = require('axios');

export function Request_get(url, data) {
	return new Promise((resolv, reject) => {
		Axios.get(url, {
			params: data
		}).then(res => {
			resolv(res);
		}).catch(err => {
			reject(err);
		})
	})
}

export function Request_post(url, data) {
	return new Promise((resolv, reject) => {
		Axios.post(url, data).then(res => {
			resolv(res);
		}).catch(err => {
			reject(err);
		})
	})
}

export function Request_put(url, data) {
	return new Promise((resolv, reject) => {
		Axios.put(url, data).then(res => {
			resolv(res);
		}).catch(err => {
			reject(err);
		})
	})
}

export function Request_delete(url, data) {
	return new Promise((reoslve, reject) => {
		Axios.delete(url, {
			params: data
		}).then(res => {
      reoslve(res);
		})
	}).catch(err => {
    reject(err);
	})
}

export function Request_patch(url, data) {
	return new Promise((resolv, reject) => {
		Axios.patch(url, data).then(res => {
			resolv(res);
		}).catch(err => {
			reject(err);
		})
	})
}

export function randomVariable(){
	var data = ['q','w','e','r','t','y','u','i','o','p','a','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m',
			   'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M','_'];
	return data[Math.ceil(Math.random() * 52)] + "" + data[Math.ceil(Math.random() * 52)] + "" + data[Math.ceil(Math.random() * 52)] + "" + data[Math.ceil(Math.random() * 52)] + "" + data[Math.ceil(Math.random() * 52)]+ "" + data[Math.ceil(Math.random() * 52)]+ "" + data[Math.ceil(Math.random() * 52)];
}

export var msg = ""

//全局
export const baseFileUrl = "http://localhost:8066/image/";

