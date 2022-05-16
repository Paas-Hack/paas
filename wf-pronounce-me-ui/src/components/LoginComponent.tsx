import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LoginLogo from '../images/login.png';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Card from '@mui/material/Card';
import ApiService from './ApiService';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
  const navigate = useNavigate();

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const userObj = {
      username: data.get('email'),
      password: data.get('password'),
    };
    ApiService.login(userObj)
    	.then((res:any) => { 
        sessionStorage.setItem('username', userObj.username+"");
        console.log('---res---', res);
        sessionStorage.setItem('jwtToken', res.data && res.data.token);
        getUserData(userObj.username);
      })
    	.catch((err:any)=> { 
			console.log('---error---', err); 
			sessionStorage.setItem('userData', '{}');
      });
  };
  
  
  
  const getUserData = (uId:any) => {
	  ApiService.getUserData(uId)
    	.then((res:any) => { 
			console.log('---userData res---', res);
			sessionStorage.setItem('userData', JSON.stringify(res.data));
			sessionStorage.setItem('loggedInUserData', JSON.stringify(res.data));
			navigate('/home');
      })
    	.catch((err:any)=> { 
			console.log('---error---', err); 
			sessionStorage.setItem('userData', '{}');
      });
  };

  return (
        <Card className="loginCard">
	        <div className="loginLogoImg">
	            <img src={LoginLogo} alt="Login" role="img" />
	        </div>
	          <Box component="form" className="formFields" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
	            <TextField
	              margin="normal"
	              required
	              fullWidth
	              id="email"
	              label="Username/Email Address"
	              name="email"
	              autoComplete="email"
	              autoFocus
	            />
	            <TextField
	              margin="normal"
	              required
	              fullWidth
	              name="password"
	              label="Password"
	              type="password"
	              id="password"
	              autoComplete="current-password"
	            />
	            <FormControlLabel
	              control={<Checkbox value="remember" color="primary" />}
	              label="Remember me"
	            />
	            <Button
	              type="submit"
	              fullWidth
	              variant="contained"
	              sx={{ mt: 3, mb: 2 }}
	            >
	              Sign In
	            </Button>
	            <Grid container>
	              <Grid item>
	                <Link href="#" variant="body2">
	                  Forgot password?
	                </Link>
	              </Grid>
	              <Grid item>
	                <Link href="#" variant="body2">
	                  {"Don't have an account? Sign Up"}
	                </Link>
	              </Grid>
	            </Grid>
	          </Box>
        </Card>
  );
}