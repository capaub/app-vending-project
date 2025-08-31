import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login(
      this.username,
      this.password,
      true,
      'password').subscribe(
      response => {
        this.router.navigate(['stock']);  // Redirige vers la page ... après connexion
      },
      error => {
        this.errorMessage = 'Invalid username or password';
      }
    );
  }
}
