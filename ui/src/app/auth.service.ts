import {Injectable} from "@angular/core"
import {Router} from "@angular/router"
import {HttpClient} from "@angular/common/http"
import {environment} from "../environments/environment"

interface AuthRequest {
  username: string
  password: string
}

@Injectable({
  providedIn: "root"
})
export class AuthService {

  constructor(private router: Router, private httpClient: HttpClient) {
  }

  checkToken(): void {
    const token = localStorage.getItem("token")
    if (!token) this.router.navigate(["login"])
  }

  createAuthRequest(username: string, password: string): AuthRequest {
    return {
      username,
      password
    }
  }

  signup(username: string, password: string) {
    const body: AuthRequest = this.createAuthRequest(username, password)
    return this.httpClient.post(`${environment.serverUrl}/users/signup`, body)
  }

  login(username: string, password: string) {
    const body: AuthRequest = this.createAuthRequest(username, password)
    return this.httpClient.post(`${environment.serverUrl}/users/login`, body)
  }

  redirectTo(url:string) {
   window.location.href = `${environment.clientUrl}/${url}`
  }
}
