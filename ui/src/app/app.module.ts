import {BrowserModule} from "@angular/platform-browser"
import {NgModule} from "@angular/core"

import {AppComponent} from "./app.component"
import {RouterModule} from "@angular/router"
import {UnknownComponent} from "./unknown/unknown.component"
import {LoginComponent} from "./login/login.component"
import {RegistrationComponent} from "./registration/registration.component"
import {AuthService} from "./auth.service"
import {FormsModule} from "@angular/forms"
import {HttpClientModule} from "@angular/common/http"
import { LogoutComponent } from './logout/logout.component';
import { ChatComponent } from './chat/chat.component'
import {ChatService} from "./chat/chat.service";
import { MemberComponent } from './chat/member/member.component'


@NgModule({
  declarations: [
    AppComponent,
    UnknownComponent,
    LoginComponent,
    RegistrationComponent,
    LogoutComponent,
    ChatComponent,
    MemberComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouterModule.forRoot([
      {path: "chat", component: ChatComponent },
      {path: "logout", component: LogoutComponent},
      {path: "notfound", component: UnknownComponent},
      {path: "signup", component: RegistrationComponent},
      {path: "login", component: LoginComponent},
      {path: "**", redirectTo: "notfound", pathMatch: "full"}
    ]),
    FormsModule
  ],
  providers: [AuthService, ChatService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
