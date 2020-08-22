import {Component, OnInit} from '@angular/core'
import {ChatService} from "./chat.service"
import {AuthService} from "../auth.service"
import {ResponseGetAllChatMessage, Member, ChatMessage} from "./interfaces"
import {map} from "rxjs/operators"


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private chatService: ChatService, private  authService: AuthService) {
  }

  sendMessage() {
    if (!this.pickedMember) return
    const curUsername = localStorage.getItem("user")
    const receiver: string = this.pickedMember?.username
    if (!receiver || !curUsername || !this.newMessage) return
    this.chatService.sendMessage(curUsername, this.newMessage, receiver)
    const formattedDate = this.chatService.formatDate(new Date())
    this.openChatMessages.push({
      text: this.newMessage,
      date: formattedDate,
      isOutgoingMessage: true
    })
    this.newMessage = null
  }

  chatMembers: Member[] = []
  pickedMember: Member
  newMessage: string
  openChatMessages: ChatMessage[]


  pickMember(member: Member) {
    const currentUsername: string = localStorage.getItem("user")
    if (!currentUsername) return
    const modifyResponse: (n: ResponseGetAllChatMessage) => ChatMessage = (n: ResponseGetAllChatMessage) => {
      const formattedDate = this.chatService.formatDate(new Date(n.time))
      return {
        isOutgoingMessage: currentUsername === n.from,
        text: n.text,
        date: formattedDate
      }
    }
    this.pickedMember = member
    this.chatService.getChatMessagesWithFriend(member.username)
      .pipe(map((response: ResponseGetAllChatMessage[]) => response.map(modifyResponse)))
      .subscribe((messages: ChatMessage[]) => this.openChatMessages = messages)
  }

  ngOnInit(): void {
    this.authService.checkToken()
    const username = localStorage.getItem("user")
    this.chatService.connect(username)
    this.chatService.findAllMembers().subscribe(result => this.chatMembers = (result as Member[]), (error) => console.log(error))
  }

}
