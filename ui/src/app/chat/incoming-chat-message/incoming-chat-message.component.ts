import {Component, Input} from '@angular/core'

@Component({
  selector: 'app-incoming-chat-message',
  templateUrl: './incoming-chat-message.component.html',
  styleUrls: ['./incoming-chat-message.component.css']
})
export class IncomingChatMessageComponent {

  @Input()
  message: string
  @Input()
  time: string
}
