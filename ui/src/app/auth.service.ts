import {Injectable} from "@angular/core"
import {Router} from "@angular/router"
import {HttpClient} from "@angular/common/http"
import {environment} from "../environments/environment"

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

  signup(username: string, password: string) {
    const body: { username: string, password: string } = {
      username,
      password
    }
    return this.httpClient.post(`${environment.url}/users`, body)
  }

  login(username: string, password: string) {
  }
}
