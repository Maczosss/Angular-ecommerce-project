import { Component, Inject, OnInit } from '@angular/core';
import { OktaAuth } from '@okta/okta-auth-js';
import { OKTA_AUTH, OktaAuthStateService } from '@okta/okta-angular';
import OktaSignIn from '@okta/okta-signin-widget';

@Component({
  selector: 'app-login-status',
  templateUrl: './login-status.component.html',
  styleUrls: ['./login-status.component.css']
})
export class LoginStatusComponent implements OnInit {

  isAuthenticated: boolean = false;
  userFullName: string = '';

  storage: Storage = sessionStorage;

  constructor(private oktaAuthService: OktaAuthStateService,
    @Inject(OKTA_AUTH) private oktaAuth: OktaAuth) { }

  ngOnInit(): void {
    //Subscribe to authentication state changes
    this.oktaAuthService.authState$.subscribe(
      (result) => {
        this.isAuthenticated = result.isAuthenticated!;
        this.getUserDetails();
      }
    )
  }

  getUserDetails() {

    if(this.isAuthenticated){
      //fetch the logged in user details(user's claims)
      //user full name is exposed as property name
      this.oktaAuth.getUser().then(
        (res) => {
          this.userFullName = res.name as string;

          //retrive user's email from authentication
          const theEmail = res.email;

          //store email in browser storage
          this.storage.setItem('userEmail', JSON.stringify(theEmail));
        }
      );
    }

  }

  logout(){
    //terminate the session with Okta and remove current tokens
    this.oktaAuth.signOut();
  }
}
