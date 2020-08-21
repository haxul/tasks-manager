import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutgoingChatMessageComponent } from './outgoing-chat-message.component';

describe('OutgoingChatMessageComponent', () => {
  let component: OutgoingChatMessageComponent;
  let fixture: ComponentFixture<OutgoingChatMessageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutgoingChatMessageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutgoingChatMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
