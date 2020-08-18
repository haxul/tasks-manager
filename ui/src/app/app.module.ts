import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {UnknownComponent} from './unknown/unknown.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';

@NgModule({
  declarations: [
    AppComponent,
    UnknownComponent,
    LoginComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      {path: "notfound", component: UnknownComponent},
      {path: "signup", component: RegistrationComponent},
      {path: "login", component: LoginComponent},
      {path: '**', redirectTo: "notfound", pathMatch: 'full'}
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
