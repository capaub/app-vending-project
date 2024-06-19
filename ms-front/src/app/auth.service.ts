import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = 'http://localhost:8076/token';

  constructor(private http: HttpClient) {
  }

  login(
    username: string,
    password: string,
    withRefreshToken: boolean,
    grantType: string,
    refreshToken?: string): Observable<any> {
    const headers = new HttpHeaders({"Content-type":"application/x-www-form-urlencoded"});
    let body = `username=${username}&password=${password}&withRefreshToken=${withRefreshToken}&grantType=${grantType}`;

    if (refreshToken) {
      body += `&refreshToken=${encodeURIComponent(refreshToken)}`;
    }

    return this.http.post<any>(this.loginUrl, body, { headers }).pipe(
      map(response => {
        if (response.token) {
          localStorage.setItem("token", response.token);
          if (response.refreshToken) {
            localStorage.setItem("refreshToken", response.refreshToken);
          }
        }
        return response;
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  public get loggedIn(): boolean {
    return localStorage.getItem('token') !== null;
  }

  public getToken(): string | null {
    return localStorage.getItem('token');
  }
}
