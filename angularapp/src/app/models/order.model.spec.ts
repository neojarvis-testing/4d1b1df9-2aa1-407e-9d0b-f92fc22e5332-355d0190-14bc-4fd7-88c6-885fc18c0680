import { Order } from './order.model';
import { User } from './user.model';
import { OrderItem } from './order-item.model';

describe('Order Model', () => {

  fit('Frontend_Order_model_should_create_an_instance', () => {
    // Create a sample User object
    const user: User = {
      userId: 1,
      email: 'user@example.com',
      password: 'password123',
      username: 'exampleUser',
      mobileNumber: '1234567890',
      userRole: 'USER'
    };

    // Create a sample Order object
    const order: Order = {
      orderDate: '2024-11-10',
      orderStatus: 'Pending',
      shippingAddress: '1234 Sample St, Sample City',
      billingAddress: '1234 Sample St, Sample City',
      totalAmount: 199.97,
      user: user,
      orderItems: []
    };

    expect(order).toBeTruthy();
    expect(order.orderDate).toBe('2024-11-10');
    expect(order.orderStatus).toBe('Pending');
    expect(order.shippingAddress).toBe('1234 Sample St, Sample City');
    expect(order.billingAddress).toBe('1234 Sample St, Sample City');
    expect(order.totalAmount).toBe(199.97);
  });

});
