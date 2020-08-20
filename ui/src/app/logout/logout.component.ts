import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment"

@Component({
  selector: 'app-logout',
  template: ''
})
export class LogoutComponent implements OnInit {

  ngOnInit(): void {
    localStorage.removeItem("token")
    localStorage.removeItem("user")
    window.location.href = `${environment.clientUrl}/login`
  }

}
