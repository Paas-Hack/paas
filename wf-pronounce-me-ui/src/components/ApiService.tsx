import axios from "axios";


class ApiService {

	httpAxios:any = axios.create({
		baseURL: "http://localhost:8080/paas",
		headers: { "Content-Type": "application/json"}
	});
	
	
	login(userObj:any){
		return this.httpAxios.post("/user/login", userObj);
	}
	
	findUsers(searchKey: any){
		return this.httpAxios.get("/users/"+searchKey);
	}
	
	getUserData(userId:any){
		return this.httpAxios.get("/user/"+userId);
	}	
	
	getUserRecordings(userId:any){
		return this.httpAxios.get("/user/"+userId+"/recording");
	}
}

export default new ApiService;