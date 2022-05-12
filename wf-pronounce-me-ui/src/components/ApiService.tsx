import axios from "axios";


class ApiService {

	httpAxios:any = axios.create({
		baseURL: "http://localhost:8080/paas",
		headers: { "Content-Type": "application/json"}
	});
	
	
	login(userObj:any){
		return this.httpAxios.post("/login", userObj);
	}	
	
	
}

export default new ApiService;