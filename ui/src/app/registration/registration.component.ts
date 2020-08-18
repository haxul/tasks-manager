import {Component, OnInit} from "@angular/core"
import {Router} from "@angular/router"

@Component({
  selector: "app-registration",
  templateUrl: "./registration.component.html",
  styleUrls: ["./registration.component.css"]
})
export class RegistrationComponent {

  constructor(public router: Router) {
  }

  password: string
  username: string
  passControl: string

  signup():void {
    console.log(this.password)
    console.log(this.passControl)
    console.log(this.username)
  }

}
