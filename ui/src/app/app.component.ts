import {AfterViewInit, Component, OnInit} from "@angular/core"
import {Router} from "@angular/router"
import {AuthService} from "./auth.service"
import {environment} from "../environments/environment"

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements  AfterViewInit {
  title = "services for live"
  isAuthPage: boolean = false

  constructor(private router: Router, private authService: AuthService) {
  }


  ngAfterViewInit(): void {
    const url = window.location.href.replace(environment.clientUrl, "")
    setTimeout(() => this.isAuthPage = url === "/login" || url === "/signup")
  }

}
