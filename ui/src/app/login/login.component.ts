import {Component} from '@angular/core'
import {AuthService} from "../auth.service"
import {Result} from "../dto/result"

interface Validation {
  result: Result,
  message: string
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private authService: AuthService) {
  }

  username: string
  password: string
  errorMessage: string

  login() {
    this.errorMessage = null
    const validation: Validation = this.validate(this.username, this.password)
    if (validation.result === Result.FAILURE) {
      this.errorMessage = validation.message
      return
    }
    this.authService.login(this.username, this.password).subscribe(
      (successResp: { username: string, token: string }) => {
        localStorage.setItem("token", successResp.token)
        localStorage.setItem("user" , successResp.username)
        this.authService.redirectTo("chat")
        },
      (errorResp: {error: {status: string, reason: string}}) => this.errorMessage = errorResp.error.reason
    )
  }

  validate(username: string, password: string): Validation {
    if (!username) return this.validationFactory(Result.FAILURE, "username is empty")
    if (!password) return this.validationFactory(Result.FAILURE, "password is empty")
    return this.validationFactory(Result.SUCCESS, "OK")
  }

  validationFactory(result: Result, message: string): Validation {
    return {
      result,
      message
    }
  }
}
