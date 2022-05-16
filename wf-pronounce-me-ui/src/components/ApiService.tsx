import axios from "axios";


class ApiService {

	httpAxios:any = axios.create({
		baseURL: "/paas",
		headers: { "Content-Type": "application/json" }
	});
	
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
}

export default new ApiService;