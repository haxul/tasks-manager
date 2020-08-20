import {Component, OnInit} from '@angular/core'
import {ChatService} from "./chat.service"
import {AuthService} from "../auth.service"

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private chatService: ChatService, private  authService: AuthService) {
  }

  sendMessage() {

    this.chatService.sendMessage("haxul", "hello from haxul", "home")

  }

  ngOnInit(): void {
    this.authService.checkToken()
    const username = localStorage.getItem("user")
    this.chatService.connect(username)
  }

}
