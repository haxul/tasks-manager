import {Component} from "@angular/core"
import {Router} from "@angular/router"
import {ErrorMessage, errorMessageFactory} from "../dto/errorMessage"
import {Result} from "../dto/result"
import {AuthService} from "../auth.service"


@Component({
  selector: "app-registration",
  templateUrl: "./registration.component.html",
  styleUrls: ["./registration.component.css"]
})
export class RegistrationComponent {

  constructor(public router: Router, private authService: AuthService) {
  }

  password: string
  username: string
  passControl: string

  errorValidateMessage: ErrorMessage


  signup(): void {
    this.clearErrors()
    const result: ErrorMessage | Result = this.validate(this.username, this.password, this.passControl)
    if (result !== Result.SUCCESS) {
      this.errorValidateMessage = result as ErrorMessage
      return
    }
    this.authService.signup(this.username, this.password).subscribe(
      success => this.router.navigate(["login"]),
      (e: { error: { error: string, reason: string } }) => this.errorValidateMessage = errorMessageFactory("", e.error.reason )
    )
  }

  clearErrors(): void {
    this.errorValidateMessage = null
  }

  validate(username: string, password: string, passControl: string): ErrorMessage | Result {
    if (!username) return errorMessageFactory("username", "Username is empty")
    if (!password) return errorMessageFactory("password", "Password is empty")
    if (!passControl) return errorMessageFactory("pass control", "Password does not match")
    if (username.length < 3) return errorMessageFactory("username", "Username length must be more 3 ")
    if (/\s+/.test(username)) return errorMessageFactory("username", "Username must not contain spaces")
    if (password.length < 8) return errorMessageFactory("password", "Password must be more than 8 letters")
    if (/\s+/.test(password)) return errorMessageFactory("password", "Password must not contain spaces")
    if (password !== passControl) return errorMessageFactory("pass control", "Passwords does not match")
    return Result.SUCCESS
  }
}
