import {Component, Input, OnInit} from '@angular/core'
import {environment} from "../../../environments/environment"

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent {


  @Input()
  defaultAvatar: string = environment.avatar.default

  @Input()
  username: string

  @Input()
  isOnline: boolean

  @Input()
  selectedUser: string

  loginedUser: string = localStorage.getItem("user")

}
