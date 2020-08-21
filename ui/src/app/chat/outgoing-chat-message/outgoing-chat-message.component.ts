import {Component, Input} from '@angular/core'

@Component({
  selector: 'app-outgoing-chat-message',
  templateUrl: './outgoing-chat-message.component.html',
  styleUrls: ['./outgoing-chat-message.component.css']
})
export class OutgoingChatMessageComponent {


  @Input()
  message: string
  @Input()
  time: string
}
