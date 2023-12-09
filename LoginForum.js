// import './Login.css';
import React from 'react';
import {
  MDBContainer,
  MDBTabs,
  MDBTabsItem,
  MDBTabsLink,
  MDBTabsContent,
  MDBTabsPane,
  MDBBtn,
  MDBIcon,
  MDBInput,
  MDBCheckbox
}
from 'mdb-react-ui-kit';
import {Button} from 'react-bootstrap';


const LoginForum = ({handleJustifyClick,handleSubmit,handleRegister,nameRef,usernameRef,emailRef,passwordRef,justifyActive}) => {
  return (

//     <Form onSubmit={handleSubmit}>
//     <Form.Group className="mb-3" controlId="formUsername">
//         <Form.Label>Username</Form.Label>
//         <Form.Control type="text" placeholder="Enter your username" ref={usernameRef} required />
//         <Form.Control.Feedback type="invalid">
//             "Please enter your username."
//         </Form.Control.Feedback>
//     </Form.Group>

//     <Form.Group className="mb-3" controlId="formPassword">
//         <Form.Label>Password</Form.Label>
//         <Form.Control type="password" placeholder="Enter your password" ref={passwordRef} required />
//         <Form.Control.Feedback type="invalid">
//             Please enter your password.
//         </Form.Control.Feedback>
//     </Form.Group>

//     <Button variant="outline-info" type="submit">Submit</Button>
// </Form>

  // <Container className="login-container">
  //     <Col md={{ span: 6, offset: 3 }} className="login-form">
  //       <h2 className="mb-4">Login</h2>
  //       <Form onSubmit={handleSubmit}>
  //         <Form.Group controlId="formUsername">
  //           <Form.Label>Username</Form.Label>
  //           <Form.Control type="text" placeholder="Enter your username" ref={usernameRef} required />
  //         </Form.Group>

  //         <Form.Group controlId="formPassword">
  //           <Form.Label>Password</Form.Label>
  //           <Form.Control type="password" placeholder="Enter your password" ref={passwordRef} required />
  //         </Form.Group>

  //         <Button variant="primary" type="submit">
  //           Submit
  //         </Button>
  //       </Form>
  //     </Col>
  //   </Container>

  // <div className="container">
  //     <div className="columns is-centered">
  //       <div className="column is-half">
  //         <h2 className="title is-2 has-text-centered mb-4">Login</h2>
  //         <form onSubmit={handleSubmit}>
  //           <div className="field">
  //             <label className="label">Username</label>
  //             <div className="control">
  //               <input
  //                 className="input"
  //                 type="text"
  //                 placeholder="Enter your username"
  //                 ref={usernameRef}
  //                 required
  //               />
  //             </div>
  //           </div>

  //           <div className="field">
  //             <label className="label">Password</label>
  //             <div className="control">
  //               <input
  //                 className="input"
  //                 type="password"
  //                 placeholder="Enter your password"
  //                 ref={passwordRef}
  //                 required
  //               />
  //             </div>
  //           </div>

  //           <div className="field">
  //             <div className="control">
  //               <button className="button is-primary" type="submit">
  //                 Submit
  //               </button>
  //             </div>
  //           </div>
  //         </form>
  //       </div>
  //     </div>
  //   </div>

  //   <form onSubmit={handleSubmit}>
  //   <div>
  //     <label htmlFor="username">Username:</label>
  //     <input type="text" id="username" ref={usernameRef} />
  //   </div>
  //   <div>
  //     <label htmlFor="password">Password:</label>
  //     <input type="password" id="password" ref={passwordRef} />
  //   </div>
  //   <button type="submit">Login</button>
  // </form>

  <MDBContainer className="p-3 my-5 d-flex flex-column w-50">

      <MDBTabs pills justify className='mb-3 d-flex flex-row justify-content-between'>
        <MDBTabsItem>
          <MDBTabsLink onClick={() => handleJustifyClick('tab1')} active={justifyActive === 'tab1'}>
            Login
          </MDBTabsLink>
        </MDBTabsItem>
        <MDBTabsItem>
          <MDBTabsLink onClick={() => handleJustifyClick('tab2')} active={justifyActive === 'tab2'}>
            Register
          </MDBTabsLink>
        </MDBTabsItem>
      </MDBTabs>

      <MDBTabsContent>

        <MDBTabsPane open={justifyActive === 'tab1'}>
          <div className="text-center mb-3">
            <p>Sign in with:</p>

            <div className='d-flex justify-content-between mx-auto' style={{width: '40%'}}>
              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='facebook-f' size="sm"/>
              </MDBBtn>

              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='twitter' size="sm"/>
              </MDBBtn>

              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='google' size="sm"/>
              </MDBBtn>

              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='github' size="sm"/>
              </MDBBtn>
            </div>

            <p className="text-center mt-3">or:</p>
          </div>

          <MDBInput wrapperClass='mb-4' label='Username' type='text' ref={usernameRef} required/>
          <MDBInput wrapperClass='mb-4' label='Password' type='password' ref={passwordRef} required/>

          <div className="d-flex justify-content-between mx-4 mb-4">
            <MDBCheckbox name='flexCheck' value='' id='flexCheckDefault' label='Remember me' />
            <a href="!#">Forgot password?</a>
          </div>

          <Button type='submit' onClick={handleSubmit} >Sign in</Button>
          <p className="text-center">Not a member? <a href="#!">Register</a></p>

        </MDBTabsPane>

        <MDBTabsPane open={justifyActive === 'tab2'}>

          <div className="text-center mb-3">
            <p> Sign up with:</p>

            <div className='d-flex justify-content-between mx-auto' style={{width: '40%'}}>
              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='facebook-f' size="sm"/>
              </MDBBtn>

              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='twitter' size="sm"/>
              </MDBBtn>

              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='google' size="sm"/>
              </MDBBtn>

              <MDBBtn tag='a' color='none' className='m-1' style={{ color: '#1266f1' }}>
                <MDBIcon fab icon='github' size="sm"/>
              </MDBBtn>
            </div>

            <p className="text-center mt-3">or:</p>
          </div>

          <MDBInput wrapperClass='mb-4' label='Name' type='text' ref={nameRef} required/>
          <MDBInput wrapperClass='mb-4' label='Username' type='text' ref={usernameRef} required/>
          <MDBInput wrapperClass='mb-4' label='Email' type='email' ref={emailRef} required/>
          <MDBInput wrapperClass='mb-4' label='Password' type='password' ref={passwordRef} required/>

          <div className='d-flex justify-content-center mb-4'>
            <MDBCheckbox name='flexCheck' id='flexCheckDefault' label='I have read and agree to the terms' />
          </div>

          <Button type='submit' onClick={handleRegister}>Sign up</Button>
        </MDBTabsPane>
      </MDBTabsContent>
    </MDBContainer>


  );
};

export default LoginForum