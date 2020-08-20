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
import {MainPageComponent} from './main-page/main-page.component'


@NgModule({
  declarations: [
    AppComponent,
    UnknownComponent,
    LoginComponent,
    RegistrationComponent,
    MainPageComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    RouterModule.forRoot([
      {path: "", component: MainPageComponent},
      {path: "notfound", component: UnknownComponent},
      {path: "signup", component: RegistrationComponent},
      {path: "login", component: LoginComponent},
      {path: "**", redirectTo: "notfound", pathMatch: "full"}
    ]),
    FormsModule
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
