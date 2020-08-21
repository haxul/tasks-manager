import {Component, OnInit} from '@angular/core'
import {ChatService} from "./chat.service"
import {AuthService} from "../auth.service"
import {Member} from "./member"

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private chatService: ChatService, private  authService: AuthService) {
  }

  sendMessage() {
    const curUsername = localStorage.getItem("user")
    const receiver: string = this.pickedMember?.username
    if (!receiver || !curUsername || !this.newMessage) return
    this.chatService.sendMessage(curUsername, this.newMessage, receiver)
    this.newMessage = null
  }

  chatMembers: Member[] = []
  pickedMember: Member
  newMessage: string

  pickMember(member: Member) {
    this.pickedMember = member
  }

  ngOnInit(): void {
    this.authService.checkToken()
    const username = localStorage.getItem("user")
    this.chatService.connect(username)
    this.chatService.findAllMembers().subscribe(result => this.chatMembers = (result as Member[]), (error) => console.log(error))
  }

}
