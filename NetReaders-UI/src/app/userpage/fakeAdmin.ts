export class FakeAdmin {

    username: string;
    email: string;
    password: string;
    constructor(username: string, email: string, password: string) { }
}
export const fakeAdminList: FakeAdmin[]  = [
    {username: 'John Doe', email: 'john@example.com', password: '123'},
    {username: 'Mary Moe', email: 'mary@example.com', password: '2223'},
    {username: 'July Dooley', email: 'july@example.com', password: '33323'}
];
