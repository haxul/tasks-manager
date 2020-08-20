import {Component, Input, OnInit} from '@angular/core'
import {environment} from "../../../environments/environment"

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit {

  constructor() { }

  @Input()
  defaultAvatar: string = environment.avatar.default

  @Input()
  username: string


  ngOnInit(): void {

  }

}
