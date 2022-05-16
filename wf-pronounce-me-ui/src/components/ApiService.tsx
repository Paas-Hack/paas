import axios from "axios";


class ApiService {


	httpAxios:any = axios.create({
		baseURL: "http://localhost:8080/paas",
		headers: { "Content-Type": "application/json" }
	});
	
	constructor(){
		// Add a request interceptor
		this.httpAxios.interceptors.request.use(function (config:any) {
			sessionStorage.setItem('showLoader', "true");
			return config;
		}, function (error:any) {
			sessionStorage.setItem('showLoader', "false");
			return Promise.reject(error);
		});

		// Add a response interceptor
		this.httpAxios.interceptors.response.use(function (response:any) {
			sessionStorage.setItem('showLoader', "false");
			return response;
		}, function (error:any) {
			sessionStorage.setItem('showLoader', "false");
			return Promise.reject(error);
		});
	}

	getHeaders(){
		const jwtToken = sessionStorage.getItem('jwtToken') || '';
		const headers = {
		  'Content-Type': 'application/json',
		  'Authorization': 'Bearer '+jwtToken
		}
		return headers;
	}
	
	getBlobHeaders(){
		const jwtToken = sessionStorage.getItem('jwtToken') || '';
		const headers = {
		  'Content-Type': 'multipart/form-data',
		  'Authorization': 'Bearer '+jwtToken
		}
		return headers;
	}
	
	
	login(userObj:any){
		return this.httpAxios.post("/user/login", userObj);
	}
	
	findUsers(searchKey: any){
		return this.httpAxios.get("/users/"+searchKey, { headers: this.getHeaders() });
	}
	
	getUserData(userId:any){
		return this.httpAxios.get("/user/"+userId, { headers: this.getHeaders() });
	}	
	
	getUserRecordings(userId:any){
		return this.httpAxios.get("/user/"+userId+"/recording", { headers: this.getHeaders() });
	}
	
	getRecording(userName: any){
		return this.httpAxios.get("/file/standard/"+userName, { headers: this.getHeaders(), responseType: 'blob' });
	}
	
	uploadUserRecordings(userId:any, payload: any){
		return this.httpAxios.post("/user/"+userId+"/primary/true/recording", payload, { headers: this.getBlobHeaders() });
	}
	
	uploadUserSubscription(userId:any, subOpt: any){
		return this.httpAxios.post("/user/"+userId+"/subscribe/"+subOpt, {}, { headers: this.getHeaders() });
	}

	deleteUserRecordings(userId:any, rId: any){
		return this.httpAxios.delete("/user/"+userId+"/recording/"+rId, {}, { headers: this.getHeaders() });
	}
}

export default new ApiService;